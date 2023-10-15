package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserPageController {
    private final UserService userService;

    @RequestMapping(path = "users/{id}.html")
    public ModelAndView getUserPage(@PathVariable(name = "id") Long id){
        UserDto user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("user-page");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(path = "users.html")
    public ModelAndView getUserPage(){
        List<UserDto> user = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView("all-users-page");
        modelAndView.addObject("users", user);
        return modelAndView;
    }
}
