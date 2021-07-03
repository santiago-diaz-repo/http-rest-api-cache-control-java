package com.cache.control.controller;

import com.cache.control.model.User;
import com.cache.control.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.WebRequest;

import javax.xml.ws.Response;

@RestController
@RequestMapping(value = "/api/last-modified",produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersLastModifiedController {

    private UserService userService;

    @Autowired
    public UsersLastModifiedController(UserService userService){
        this.userService=userService;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id, WebRequest request){
        return processUserRequest(id, request);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User us,WebRequest request){
        return processUserRequest(id, request);
    }

    private ResponseEntity<User> processUserRequest(@PathVariable String id, WebRequest request) {
        User user = this.userService.getUser(id);
        long modificationDate = user.getLastModified().toInstant().toEpochMilli();

        if(request.checkNotModified(modificationDate))
            return null;

        CacheControl cacheControl= CacheControl.noStore();
        return ResponseEntity.ok()
                .lastModified(modificationDate)
                .cacheControl(cacheControl)
                .body(user);
    }
}
