package org.java.letsplay.controller;

import org.java.letsplay.model.Category;
import org.java.letsplay.model.Event;
import org.java.letsplay.service.CategoryService;
import org.java.letsplay.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "event/index";
    }

    @GetMapping("/sort/{sort}")
    public String indexSortBy(Model model, @PathVariable String sort) {
        model.addAttribute("events", eventService.findAllSorted(sort));
        model.addAttribute("categories", categoryService.findAll());
        return "event/index";
    }
    
    @GetMapping("/search")
    public String advancedSearch(Model model, @RequestParam(required = false) String name,  @RequestParam(required = false) Integer category_id, @RequestParam(required = false) String address){

        if (category_id != null) {
            Category category = null;
            category = categoryService.getById(category_id);
            model.addAttribute("events", eventService.advancedSearch(name, category, address));
        } else {
            model.addAttribute("events", eventService.advancedSearchNoCategory(name, address));
        }
        
        model.addAttribute("categories", categoryService.findAll());
        
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
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("edit", false);
        return "event/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("event") Event formEvent, BindingResult bindingResult,  Model model){

        if (formEvent.getImage() == "") {
            formEvent.setImage("https://media.istockphoto.com/id/1147544807/it/vettoriale/la-commissione-per-la-immagine-di-anteprima-grafica-vettoriale.jpg?s=612x612&w=0&k=20&c=gsxHNYV71DzPuhyg-btvo-QhhTwWY0z4SGCSe44rvg4=");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "event/create-or-edit";
        }

        eventService.save(formEvent);
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,  Model model) {
        model.addAttribute("edit", true);
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("categories", categoryService.findAll());

        return "event/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("event") Event formEvent, BindingResult bindingResult, Model model,  @PathVariable Integer id) {

        if (formEvent.getImage() == "") {
            formEvent.setImage("https://media.istockphoto.com/id/1147544807/it/vettoriale/la-commissione-per-la-immagine-di-anteprima-grafica-vettoriale.jpg?s=612x612&w=0&k=20&c=gsxHNYV71DzPuhyg-btvo-QhhTwWY0z4SGCSe44rvg4=");
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("categories", categoryService.findAll());
            return "event/create-or-edit";
        }

        eventService.save(formEvent);        
        return "redirect:/events/{id}";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        eventService.deleteById(id);
        return "redirect:/events";
    }    


}
