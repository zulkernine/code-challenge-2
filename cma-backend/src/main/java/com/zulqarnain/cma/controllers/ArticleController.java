package com.zulqarnain.cma.controllers;

import com.zulqarnain.cma.exception.ApiException;
import com.zulqarnain.cma.models.Articles;
import com.zulqarnain.cma.models.DraftArticles;
import com.zulqarnain.cma.payload.request.ArticleRequest;
import com.zulqarnain.cma.repository.ArticleRepository;
import com.zulqarnain.cma.repository.DraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    List<Articles> getAllArticles(@RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "") String search){
        if(search.isEmpty())
            return articleRepository.findAll();
        else
            return articleRepository.findAllByText(search);
    }

    @GetMapping("/drafts")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    List<DraftArticles> getAllDrafts(){
        return draftRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Articles> getArticle(@PathVariable String id){
        Optional<Articles> articles = articleRepository.findById(id);
        if(articles.isPresent()){
            return articles.map(ResponseEntity::ok).get();
        }else{
            throw new ApiException("Article not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteArticle(@PathVariable String id){
        Optional<Articles> articles = articleRepository.findById(id);
        if(userController.getUser()!=null && Objects.equals(userController.getUser().getId(), articles.get().getAuthorId())){
            articleRepository.deleteById(id);
            return new ResponseEntity("Article deleted",HttpStatus.ACCEPTED);
        }else
            throw new ApiException("Article not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/drafts/{id}")
    ResponseEntity deleteDraft(@PathVariable String id){
        Optional<DraftArticles> draftArticles = draftRepository.findById(id);
        if(userController.getUser()!=null && Objects.equals(userController.getUser().getId(), draftArticles.get().getAuthorId())){
            draftRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            throw new ApiException("Draft not found", HttpStatus.NOT_FOUND);
        }
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
        }else{
            throw new ApiException("Draft not found", HttpStatus.NOT_FOUND);
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
        }else{
            throw new ApiException("Draft not found", HttpStatus.NOT_FOUND);
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

    void editArticles(ArticleRequest article){
        if(article.getId()!=null){
            Optional<DraftArticles> optional = draftRepository.findById(article.getId());
            if(optional.isPresent()){
                DraftArticles draft = optional.get();
                draft.setDisApproved(false);
                draft.setContent(article.getContent());
                draftRepository.save(draft);
            }else{
                throw new ApiException("Draft not found", HttpStatus.NOT_FOUND);
            }
        }else {
            draftRepository.save(new DraftArticles(article.getAuthorName(),article.getContent(),article.getAuthorId(), article.getOriginalArticleId()));
        }
    }

}
