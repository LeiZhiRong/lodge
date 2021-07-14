package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@Scope("prototype")
@RequestMapping("/main/")
@AuthClass("login")
public class MainController {

    @AuthMethod
    @GetMapping("index")
    public ModelAndView list() throws JsonProcessingException {
        return new ModelAndView("index");
    }
}
