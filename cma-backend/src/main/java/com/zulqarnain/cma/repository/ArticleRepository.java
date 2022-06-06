package com.zulqarnain.cma.repository;

import com.zulqarnain.cma.models.Articles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Articles,String> {

    List<Articles> findAll();

    @Query("{$text:{$search: ?0}}")
    List<Articles> findAllByText(String text);

    List<Articles> findAllByAuthorId(String authorId);
}
