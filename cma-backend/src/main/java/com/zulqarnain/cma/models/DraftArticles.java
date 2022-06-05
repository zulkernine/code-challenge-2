package com.zulqarnain.cma.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drafts")
public class DraftArticles extends Articles{
    private String originalArticleId;

    private boolean isDisApproved=false;

    public boolean isDisApproved() {
        return isDisApproved;
    }

    public void setDisApproved(boolean disApproved) {
        isDisApproved = disApproved;
    }

    public String getOriginalArticleId() {
        return originalArticleId;
    }

    public void setOriginalArticleId(String originalArticleId) {
        this.originalArticleId = originalArticleId;
    }

    public DraftArticles() {
    }

    public DraftArticles(String title, String content, String authorId, String originalArticleId) {
        super(title, content, authorId);
        this.originalArticleId = originalArticleId;
    }
}
