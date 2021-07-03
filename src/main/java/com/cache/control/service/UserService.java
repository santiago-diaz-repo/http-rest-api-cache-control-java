package com.cache.control.service;

import com.cache.control.model.User;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;

@Service
public class UserService {

    public User getUser(String id){
        if(id.equals("1234"))
            return new User("1234","Test1", "Test1", new Date(121, Calendar.JULY,1, 17,39,23));

        return new User("9876","Test2","Test2",new Date());
    }
}
