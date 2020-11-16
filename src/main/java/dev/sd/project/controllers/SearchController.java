package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.model.solr.ArticleSolr;
import dev.sd.project.repository.ArticleRepository;
import dev.sd.project.repository.solr.ArticleSolrRepository;
import dev.sd.project.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final ArticleRepository articleRepository;
    private final ArticleSolrRepository articleSolrRepository;

    @GetMapping
    public ModelAndView searchResultView(@RequestParam(name = "q") String query) {
        ModelAndView modelAndView = new ModelAndView("searchResult");

        Set<ArticleSolr> resultSolr = new HashSet<>();
        resultSolr.addAll(articleSolrRepository.findAllByTitle(query));
        resultSolr.addAll(articleSolrRepository.findAllByContent(query));
        List<Article> results = resultSolr.stream()
                .map(e -> articleRepository.findById(e.getArticleId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        modelAndView.addObject("results", results);
        modelAndView.addObject("query", query);

        return modelAndView;
    }
}
