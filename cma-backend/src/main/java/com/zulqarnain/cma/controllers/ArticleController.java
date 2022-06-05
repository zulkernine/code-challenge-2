package com.zulqarnain.cma.controllers;

import com.zulqarnain.cma.models.Articles;
import com.zulqarnain.cma.models.DraftArticles;
import com.zulqarnain.cma.models.subComponent.ERole;
import com.zulqarnain.cma.payload.request.ArticleRequest;
import com.zulqarnain.cma.repository.ArticleRepository;
import com.zulqarnain.cma.repository.DraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    DraftRepository draftRepository;

    @Autowired
    UserController userController;

    @GetMapping
    List<Articles> getAllArticles(@RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "10") int pageSize){
        return articleRepository.findAll();
    }

    @GetMapping("/drafts")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    List<DraftArticles> getAllDrafts(){
        return draftRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Articles> getArticle(@PathVariable String id){
        Optional<Articles> articles = articleRepository.findById(id);
        return articles.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteArticle(@PathVariable String id){
        Optional<Articles> articles = articleRepository.findById(id);
        if(userController.getUser()!=null && Objects.equals(userController.getUser().getId(), articles.get().getAuthorId())){
            articleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }else
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/drafts/{id}")
    ResponseEntity deleteDraft(@PathVariable String id){
        Optional<DraftArticles> draftArticles = draftRepository.findById(id);
        if(userController.getUser()!=null && Objects.equals(userController.getUser().getId(), draftArticles.get().getAuthorId())){
            draftRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }else
            return ResponseEntity.badRequest().build();
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    void approve(@PathVariable String id){
        Optional<DraftArticles> opt = draftRepository.findById(id);
        if(opt.isPresent()){
            DraftArticles draft = opt.get();
            if(draft.getOriginalArticleId() != null){
                Articles articles = articleRepository.findById(draft.getOriginalArticleId()).get();
                articles.setContent(draft.getContent());
                articleRepository.save(articles);
            }else{
                articleRepository.save(new Articles(draft.getAuthorName(),draft.getContent(), draft.getAuthorId()));
            }
            draftRepository.delete(draft);
        }
    }

    @PutMapping("/disapprove/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    void disApprove(@PathVariable String id){
        Optional<DraftArticles> opt = draftRepository.findById(id);
        if(opt.isPresent()){
            DraftArticles draft = opt.get();
            draft.setDisApproved(true);
            draftRepository.save(draft);
        }
    }

    @GetMapping("/user/{id}")
    List<Articles> getAllArticlesForUser(@PathVariable String id){
        return articleRepository.findAllByAuthorId(id);
    }

    @GetMapping("/drafts/self")
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    List<DraftArticles> getAllDraftsForUser(){
        String id = userController.getUser().getId();
        return draftRepository.findAllByAuthorId(id);
    }

    @PostMapping("/drafts")
    void saveNewArticle(@RequestBody ArticleRequest article){
        editArticles(article);
    }

//    @PostMapping("/drafts/{id}")
    void editArticles(ArticleRequest article){
        if(article.getId()!=null){
            DraftArticles draft = draftRepository.findById(article.getId()).get();

            draft.setDisApproved(false);
            draft.setContent(article.getContent());
            draftRepository.save(draft);
        }else {
            draftRepository.save(new DraftArticles(article.getAuthorName(),article.getContent(),article.getAuthorId(), article.getOriginalArticleId()));
        }
    }

}
