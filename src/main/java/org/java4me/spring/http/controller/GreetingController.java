package org.java4me.spring.http.controller;

import org.java4me.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {
    @GetMapping("/hello")
    public String hello(Model model,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto) {

        model.addAttribute("user", userReadDto);

        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user) {
        return "greeting/bye";
    }

    @GetMapping("/hello/{id}")
    public ModelAndView hello2(@RequestParam("age") Integer age,
                              @RequestHeader("accept") String accept,
                              @CookieValue("JSESSIONID") String jsessionId,
                              @PathVariable("id") Integer id,
                              ModelAndView modelAndView) {
        modelAndView.setViewName("greeting/hello");

        return modelAndView;
    }
}
