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
    private static final String[] RANDOM_IMGS_CALCIO = {
        "https://www.lineaedp.it/files/2021/05/calcio.jpeg",
        "https://www.cartadiroma.org/wp-content/uploads/2014/08/pallone-calcio-1-1.jpg",
        "https://cdn.skuola.net/news_foto/image-grabber/image-64621625e9ba2.jpg"
    };
    private static final String[] RANDOM_IMGS_PALLAVOLO = {
        "https://buonenotizie.it/wp-content/uploads/2024/06/pallavolo-femminile.jpg",
        "https://www.bologna24ore.it/wp-content/uploads/sites/7/2023/08/Pallavolo-696x392.jpg",
        "https://adastravolley.com/wp-content/uploads/2023/05/Palla-regolamentare-nella-pallavolo.jpg"
    };
    private static final String[] RANDOM_IMGS_CARTE = {
        "https://www.itismagazine.it/files/2022/05/giochi-di-carte-.jpeg",
        "https://www.ipersoap.com/wp-content/uploads/2021/09/Cover-Carte.png.webp",
        "https://www.unitretrofarello.it/wp-content/uploads/2024/08/Burraco.jpg"
    };
    private static final String[] RANDOM_IMGS_SAGRA_ALIMENTARE = {
        "https://blog.academia.tv/wp-content/uploads/2023/10/variation-homemade-baked-pastry-cuisine-shop-1-min.jpg",
        "https://www.turismovacanze.net/wp-content/uploads/2019/01/sagra.jpg",
        "https://wips.plug.it/cips/initalia.virgilio.it/cms/2016/07/sagre-italiane.jpg"
    };
    public static String chooseRandomImage(Integer categoryId) {
        Random random = new Random();

        if (categoryId == 1 || categoryId == 2 || categoryId == 3 || categoryId == 4) {
            int index = random.nextInt(RANDOM_IMGS_CALCIO.length);
            return RANDOM_IMGS_CALCIO[index];
        } else if (categoryId == 5 || categoryId == 6 ) {
            int index = random.nextInt(RANDOM_IMGS_PALLAVOLO.length);
            return RANDOM_IMGS_PALLAVOLO[index];
        } else if (categoryId == 7 ) {
            int index = random.nextInt(RANDOM_IMGS_CARTE.length);
            return RANDOM_IMGS_CARTE[index];
        } else if (categoryId == 8 ) {
            int index = random.nextInt(RANDOM_IMGS_SAGRA_ALIMENTARE.length);
            return RANDOM_IMGS_SAGRA_ALIMENTARE[index];
        } 
        return  "https://i.postimg.cc/dtJS98Kr/hero-Image.png";
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


        formEvent.setMainImage(chooseRandomImage(formEvent.getCategory().getId()));
        

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
