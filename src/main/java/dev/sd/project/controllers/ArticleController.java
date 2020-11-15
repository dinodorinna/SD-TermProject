package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import dev.sd.project.model.solr.ArticleSolr;
import dev.sd.project.repository.ArticleRepository;
import dev.sd.project.repository.UserRepository;
import dev.sd.project.repository.solr.ArticleSolrRepository;
import dev.sd.project.service.ArticleService;
import dev.sd.project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.logging.Level;

@Log
@Controller
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final ArticleSolrRepository articleSolrRepository;

    @GetMapping
    public ModelAndView articleView(@RequestParam(name = "id", required = false) Optional<String> articleId) {

        if (articleId.isPresent()) {
            Optional<Article> article = articleRepository.findById(articleId.get()); //เอาไว้อ่านค่า articleIdใน DB
            // Optional == check null

            if (article.isPresent()) {
                ModelAndView articleModel = new ModelAndView("article");
                articleModel.addObject(article.get());

                boolean isOwner = false;
                String currentUserId = userService.getCurrentUserId();
                if (currentUserId != null) {
                    isOwner = currentUserId.equals(article.get().getWriter().getUserId());
                }
                articleModel.addObject("isOwner", isOwner);

                articleService.countArticleVisitor(article.get());

                return articleModel;
            }
        }

        return new ModelAndView("articleNotFound");
    }

    @PostMapping("/create")
    public RedirectView createArticle(String title,
                                      String description,
                                      String content,
                                      String[] tag)  {
        User user = userRepository.findByUserId(userService.getCurrentUserId());

        if (user == null) {
            log.log(Level.INFO, "User is null");
        } else {
            try {
                Article article = articleService.createArticle(user,title,description,content,new HashSet<>(Arrays.asList(tag)));

                ArticleSolr articleSolr = new ArticleSolr();
                articleSolr.setArticleId(article.getArticleId());
                articleSolr.setWriterName(article.getWriter().getUsername());
                articleSolr.setTitle(article.getTitle());
                articleSolr.setContent(article.getContent());

                articleSolrRepository.save(articleSolr);

                return new RedirectView("/article?id="+article.getArticleId());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new RedirectView("/article/create");

}

    @GetMapping("/create")
    public ModelAndView createArticleForm(){
        return new ModelAndView("articleCreateForm");

    }

    @PostMapping ("/edit")
    public RedirectView editArticle(String articleId,
                                    String title,
                                    String description,
                                    String content,
                                    String[] tag){
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null || !userService.getCurrentUserId().equals(article.getWriter().getUserId())){
            return new RedirectView("/");
        }

        try {
            articleService.editArticle(articleId,title,description,content,new HashSet<>(Arrays.asList(tag)));

            Optional<ArticleSolr> solrOptional = articleSolrRepository.findById(articleId);
            ArticleSolr articleSolr;
            articleSolr = solrOptional.orElseGet(ArticleSolr::new);

            articleSolr.setArticleId(article.getArticleId());
            articleSolr.setWriterName(article.getWriter().getUsername());
            articleSolr.setTitle(article.getTitle());
            articleSolr.setContent(article.getContent());

            articleSolrRepository.save(articleSolr);

            return new RedirectView("/article?id="+articleId);
        } catch (Exception e) {
            e.printStackTrace();

            return new RedirectView("/article/edit?id="+articleId);
        }

    }
    
    @GetMapping("/edit")
    public ModelAndView editArticleForm(@RequestParam(name = "id")String articleId){
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null){
            return new ModelAndView("articleNotFound");
        }

        if (!userService.getCurrentUserId().equals(article.getWriter().getUserId())) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView articleCreateForm = new ModelAndView("articleCreateForm");
        articleCreateForm.addObject(article);

        return articleCreateForm;


    }
    @GetMapping("/delete")
    public ModelAndView deleteArticle(@RequestParam(name = "id")String articleId){
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null || !userService.getCurrentUserId().equals(article.getWriter().getUserId())){
            articleSolrRepository.deleteById(articleId);

            return new ModelAndView("redirect:/");
        }

        try {
            articleService.deleteArticle(articleId);
            return new ModelAndView("articleRemoved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("articleNotFound");
        }
    }


}
