package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.service.ArticleService;
import dev.sd.project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log
@AllArgsConstructor
public class DashboardController {
    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping (value = {"/","/dashboard"})  //multiple path
    public ModelAndView dashboardView(){
        return new ModelAndView("dashboard");
    }

    @GetMapping (value = {"/getDashboardLatest"}, produces="application/json")
    @ResponseBody
    public String dashboardLatestArticle(int page) throws JSONException, ChangeSetPersister.NotFoundException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        Page<Article> articles = articleService.getLatestArticle(page);

        articles.forEach(article -> {
            JSONObject o = new JSONObject();
            try {
                o.put("articleId", article.getArticleId());
                o.put("title",article.getTitle());
                o.put("description",article.getDescription());
                o.put("content",article.getContent());
                o.put("writerName", article.getWriter().getUsername());
                JSONArray tagArray = new JSONArray();
                article.getTag().forEach(tagArray::put);
                o.put("tag", tagArray);
                o.put("thumbnail", article.getThumbnail());
                o.put("publishDate",article.getPublishDate().getTime());
                if (article.getEditDate() != null) {
                    o.put("editDate",article.getEditDate().getTime()); //if editDate null มันจะไม่ส่ง editDateไปด้วย
                }
                o.put("favoriteCount",article.getFavoriteCount());
                o.put("visitorCount",article.getVisitorCount());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(o);
        });

        jsonObject.put("articles", jsonArray);

        return jsonObject.toString();
    }

    @GetMapping (value = {"/getDashboardByRank"}, produces="application/json")
    @ResponseBody
    public String dashboardArticleByRank(int page) throws JSONException, ChangeSetPersister.NotFoundException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        Page<Article> articles = articleService.getArticleByRank(page);

        articles.forEach(article -> {
            JSONObject o = new JSONObject();
            try {
                o.put("articleId", article.getArticleId());
                o.put("title",article.getTitle());
                o.put("description",article.getDescription());
                o.put("content",article.getContent());
                o.put("writerName", article.getWriter().getUsername());
                JSONArray tagArray = new JSONArray();
                article.getTag().forEach(tagArray::put);
                o.put("tag", tagArray);
                o.put("thumbnail", article.getThumbnail());
                o.put("publishDate",article.getPublishDate().getTime());
                if (article.getEditDate() != null) {
                    o.put("editDate",article.getEditDate().getTime());
                }
                o.put("favoriteCount",article.getFavoriteCount());
                o.put("visitorCount",article.getVisitorCount());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(o);
        });

        jsonObject.put("articles", jsonArray);

        return jsonObject.toString();
    }
}
