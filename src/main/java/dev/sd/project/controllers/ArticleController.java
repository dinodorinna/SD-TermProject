package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
    private ArticleRepository articleRepository;

    @GetMapping
    public ModelAndView articleView(@RequestParam(name = "id",required = false) Optional<String> articleId){

        if(articleId.isPresent()) {
            Optional<Article> article = articleRepository.findById(articleId.get()); //เอาไว้อ่านค่า articleIdใน DB
            // Optional == check null

            if (article.isPresent()) {
                ModelAndView articleModel = new ModelAndView("article");
                articleModel.addObject(article.get());
                return articleModel;
            }
        }

        return new ModelAndView("articleNotFound");
    }

}
