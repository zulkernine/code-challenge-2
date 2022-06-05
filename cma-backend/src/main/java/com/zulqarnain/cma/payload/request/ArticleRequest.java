package com.zulqarnain.cma.payload.request;

import javax.validation.constraints.NotBlank;

public class ArticleRequest {
    @NotBlank
    private String authorId;

    @NotBlank
    private String authorName;

    @NotBlank
    private String content;

    private String originalArticleId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArticleRequest() {
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public String getOriginalArticleId() {
        return originalArticleId;
    }

    public void setOriginalArticleId(String originalArticleId) {
        this.originalArticleId = originalArticleId;
    }
}
