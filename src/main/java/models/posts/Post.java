package models.posts;

import lombok.*;

@Data
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}