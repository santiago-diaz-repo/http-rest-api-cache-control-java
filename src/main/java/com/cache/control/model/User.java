package com.cache.control.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Date lastModified;

}
