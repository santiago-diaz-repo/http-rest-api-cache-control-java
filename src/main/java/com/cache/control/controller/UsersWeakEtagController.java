package com.cache.control.controller;

import com.cache.control.model.User;
import com.cache.control.service.UserService;
import com.cache.control.util.EtagGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/weak-etag",produces = {MediaType.APPLICATION_JSON_VALUE})
public class UsersWeakEtagController {

    private UserService userService;
    private EtagGenerator etagGenerator;

    @Autowired
    public UsersWeakEtagController(UserService userService, EtagGenerator etagGenerator){
        this.userService = userService;
        this.etagGenerator=etagGenerator;
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {

        CacheControl cacheControl = CacheControl
                .noCache();
        User u = this.userService.getUser(id);

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(u);
    }
}
