package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@Scope("prototype")
@RequestMapping("/main/")
@AuthClass("login")
public class MainController {

    @AuthMethod
    @GetMapping("index")
    public ModelAndView list(Model model, HttpSession session) throws JsonProcessingException {
         ModelAndView view = new ModelAndView("index");
        return view;
    }
}
