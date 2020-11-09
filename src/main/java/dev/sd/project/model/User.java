package dev.sd.project.model;

import lombok.AccessLevel;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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


    public User(){} //fetch data from database to JavaObject
    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String toString() { //pass JavaObject into MongoDB query
        return String.format(
                "User[id=%s, username='%s', email='%s', password='%s']",
                userId, username, email, password);
    }


}
