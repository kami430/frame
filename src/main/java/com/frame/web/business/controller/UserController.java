package com.frame.web.business.controller;

import com.frame.core.http.BusinessException;
import com.frame.web.business.entity.User;
import com.frame.web.business.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/saveuser")
    public String saveUser(@RequestBody User user){
        try{
            userServiceImpl.saveUser(user);
        }catch (Exception e){
            throw new BusinessException("hahha");
        }
        return "hahah";
    }

}
