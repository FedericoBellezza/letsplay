package org.java.letsplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping
public class HomepageController {

    @GetMapping
    public String redirect() {
        return "redirect:/events";
    }
    

    
}
