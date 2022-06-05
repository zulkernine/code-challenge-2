package com.zulqarnain.cma.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Articles {
    @Id
    private String id;

    private String authorName;

    @TextIndexed
    private String content;

    private String authorId;

    public Articles(String authorName, String content, String authorId) {
        this.authorName = authorName;
        this.content = content;
        this.authorId = authorId;
    }

    public Articles() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
