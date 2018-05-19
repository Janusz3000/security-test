package com.example.demo.controller;

import com.example.demo.exception.UserNotActivatedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model, HttpServletRequest httpServletRequest) {
        return "hello";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getUserPrincipal() != null)
            return "redirect:/index";
        else
            return "login";
    }

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/index";
    }

    @RequestMapping("/denied")
    public String denied() {
      return "denied";
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ModelAndView handleNotFoundException(UserNotActivatedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("custom");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}
