package com.zulqarnain.cma.repository;

import com.zulqarnain.cma.models.Articles;
import com.zulqarnain.cma.models.DraftArticles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DraftRepository extends MongoRepository<DraftArticles,String> {
    List<DraftArticles> findAll();

    List<DraftArticles> findAllByAuthorId(String authorId);
}
