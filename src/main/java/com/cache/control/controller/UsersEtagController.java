package com.cache.control.controller;

import com.cache.control.model.User;
import com.cache.control.service.UserService;
import com.cache.control.util.EtagGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@RestController
@RequestMapping(value = "/api/etag", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersEtagController {

    private UserService userService;
    private EtagGenerator etagGenerator;

    @Autowired
    public UsersEtagController(UserService userService, EtagGenerator etagGenerator){
        this.userService = userService;
        this.etagGenerator=etagGenerator;
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id, WebRequest request) throws JsonProcessingException {

        CacheControl cacheControl = CacheControl
                .noCache();
        User u = this.userService.getUser(id);
        String etag=this.etagGenerator.getEtag(u);

        if(request.checkNotModified(etag))
            return null;

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .eTag(etag)
                .body(u);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestHeader(HttpHeaders.IF_MATCH) String ifMatch, @RequestBody User user) throws JsonProcessingException {
        User u = this.userService.getUser(id);
        String etag = this.etagGenerator.getEtag(u);

        CacheControl cacheControl = CacheControl
                .noCache();

        String[] eTagHeader = ifMatch.split("\"");
        u.setLastModified(new Date());

        // If current etag is equal to if-match, it is possible to update this resource
        if(etag.equals(eTagHeader[1])) {
            u.setLastModified(new Date());
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .eTag(this.etagGenerator.getEtag(u))
                    .body(u);
        }

        // If current etag is not equal to if-match, it is not possible to update this resource, the consumer should
        // get the resource again
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .cacheControl(cacheControl)
                .eTag(etag)
                .body(null);
    }
}
