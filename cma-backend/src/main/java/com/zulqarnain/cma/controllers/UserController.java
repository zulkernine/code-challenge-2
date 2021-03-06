package com.zulqarnain.cma.controllers;

import com.zulqarnain.cma.exception.ApiException;
import com.zulqarnain.cma.models.Articles;
import com.zulqarnain.cma.models.DraftArticles;
import com.zulqarnain.cma.models.User;
import com.zulqarnain.cma.payload.request.ArticleRequest;
import com.zulqarnain.cma.repository.UserRepository;
import com.zulqarnain.cma.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/user")
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    User getUser(){
        String uname = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        System.out.println(uname);
        Optional<User> u = userRepository.findByUsername(uname);
        if(u.isPresent()){
            return u.get();
        }else {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }
    }



}
