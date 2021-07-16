package com.shags.lodge.controller.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import org.springframework.context.annotation.Scope;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/demo/")
@Scope("prototype")
@AuthClass("login")
public class DemoCodeController {

    @AuthMethod(role = "ROLE_ACCOUN")
    @RequestMapping("demo")
    public ModelAndView demo() throws JsonProcessingException {
        return new ModelAndView("accoun/index");
    }

}
