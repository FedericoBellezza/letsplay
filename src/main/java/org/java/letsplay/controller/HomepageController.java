package org.java.letsplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping
public class HomepageController {

    // routes
    @GetMapping
    public String redirect() {
        return "redirect:/events";
    }

}
