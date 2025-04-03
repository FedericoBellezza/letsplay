package org.java.letsplay.controller;

import org.java.letsplay.model.Event;
import org.java.letsplay.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @GetMapping
    public String index(Model model){
        model.addAttribute("events", eventService.findAll());
        return "event/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
        model.addAttribute("event", eventService.getById(id));
        return "event/show";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("edit", false);
        return "event/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("event") Event formEvent, Model model, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "event/create-or-edit";
        }

        eventService.save(formEvent);
        return "redirect:/events";
    }
    
}
