package dev.sd.project.model;

import lombok.*;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data //all data have getter&setter
@Document(collection = "user")

public class User {
    @Id //auto generate id
    @Setter(value = AccessLevel.NONE) //userId cant be change
    private String userId;

    private String username;
    private String email;

    @EqualsAndHashCode.Exclude // compare
    private String password;

    @DBRef
    private List<Article> favArticleList;


    public User(){} //fetch data from database to JavaObject

    public User(String username, String email, String password, List<Article> favArticleList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favArticleList = favArticleList;
    }

    public String toString() { //pass JavaObject into MongoDB query
        return String.format(
                "User[id=%s, username='%s', email='%s', password='%s']",
                userId, username, email, password);
    }


}
