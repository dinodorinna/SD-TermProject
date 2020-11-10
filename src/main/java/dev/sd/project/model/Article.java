package dev.sd.project.model;

import com.mongodb.lang.Nullable;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@Document(collection = "article")
public class Article {
    @Id
    @Setter(value = AccessLevel.NONE)

    @EqualsExclude// ไม่นำมาใช้เปรียบเทียบ
    private String articleId;

    @DBRef() //relation with User
    private User writer;

    private String title;
    private String description;
    private String content;  // ในการเปรียบเทียบ 2 article จำเป็นต้องใช้ writer title content เพื่อดูว่า มันใช่อันเดียวกันมั้ย
                            // เป้าหมายคือไม่ต้องการให้ writer คนเดิม โพส title และ content ซ้ำกัน

    @EqualsExclude
    private int favoriteCount;
    @EqualsExclude
    private int visitorCount;
    @EqualsExclude
    private Date publishDate;
    @Nullable // can null
    @EqualsExclude
    private Date editDate;

    private Set<String> tag;


    public Article(){}

    public Article(String articleId, User writer, String title,String description, String content,
                   Date publishDate, Date editDate, int favoriteCount, int visitorCount, Set<String> tag) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.description = description;
        this.content = content;
        this.publishDate = publishDate;
        this.editDate = editDate;
        this.favoriteCount = favoriteCount;
        this.visitorCount = visitorCount;
        this.tag = tag;
    }
    public String toString() {
        return String.format(
                "Article[id=%s, writer='%s', title='%s', content='%s'," +
                        "publishDate='%s', editDate='%s', favoriteCount='%s', visitorCount='%s']",
                articleId, writer, title, content, publishDate, editDate, favoriteCount, visitorCount);
    }

    }


