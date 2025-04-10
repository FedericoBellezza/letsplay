package org.java.letsplay.controller;

import java.util.Random;

import org.java.letsplay.model.Category;
import org.java.letsplay.model.Event;
import org.java.letsplay.service.CategoryService;
import org.java.letsplay.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // random mainImage on event creation
    private static final String[] RANDOM_IMGS = {
        "https://i.postimg.cc/dtJS98Kr/hero-Image.png",
        "https://m.media-amazon.com/images/I/411JTw5KPFL._AC_UF1000,1000_QL80_.jpg",
        "data:image/jpeg;base64,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"
    };
    public static String chooseRandomImage() {
        Random random = new Random();
        int index = random.nextInt(RANDOM_IMGS.length);
        return RANDOM_IMGS[index];
    }

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

        if (formEvent.getMainImage() == "") {
            formEvent.setMainImage(chooseRandomImage());
        }

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

        if (formEvent.getMainImage() == "") {
            formEvent.setMainImage(chooseRandomImage());
        }

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
