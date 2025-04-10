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
        "https://i.postimg.cc/VL230t0X/calcio1.png", "https://i.postimg.cc/MGSNd8DT/calcio2.png", "https://i.postimg.cc/K8LCPYtw/calcio3.png", "https://i.postimg.cc/cJBjz59c/calcio4.png", "https://i.postimg.cc/9MXSHtsH/calcio5.png", "https://i.postimg.cc/LXyW9gMY/calcio6.png"

    };
    private static final String[] RANDOM_IMGS_PALLAVOLO = {
        "https://i.postimg.cc/vBRrWQLq/pallavolo2.png", "https://i.postimg.cc/V6jjg3KY/pallavolo3.png", "https://i.postimg.cc/9QMY2gQp/pallavolo4.png", "https://i.postimg.cc/pL6YLQ9t/pallavolo5.png", "https://i.postimg.cc/Gtdv5qfv/pallavolo6.png", "https://i.postimg.cc/nr2qbq8g/pallavolo7.png"
    };
    private static final String[] RANDOM_IMGS_CARTE = {
        "https://i.postimg.cc/jqDWsVHC/carte1.png", "https://i.postimg.cc/xjBX8s55/carte2.png", "https://i.postimg.cc/hPxfd93B/carte3.png", "https://i.postimg.cc/BtWf3Px5/carte4.png", "https://i.postimg.cc/Bn0tcXd1/carte5.png", "https://i.postimg.cc/BvZ6kRZS/carte6.png", "https://i.postimg.cc/qvxqmpTD/carte7.png", "https://i.postimg.cc/Bvh60Ntx/carte8.png"
    };
    private static final String[] RANDOM_IMGS_SAGRA_ALIMENTARE = {
"https://i.postimg.cc/zv72tLxX/sagra-alimentare1.png",
"https://i.postimg.cc/CMs2LtwT/sagra-alimentare2.png",
"https://i.postimg.cc/Yq2s43S4/sagra-alimentare3.png",
"https://i.postimg.cc/rFhY5rdf/sagra-alimentare4.png",
"https://i.postimg.cc/DwHjM3Ps/sagra-alimentare5.png",
"https://i.postimg.cc/tTHr9df4/sagra-alimentare6.png",
"https://i.postimg.cc/3wGSMftq/sagra-alimentare7.png",
"https://i.postimg.cc/Ss7gR2F7/sagra-alimentare8.png",
"https://i.postimg.cc/WzNXkNsR/sagra-alimentare9.png"
    };
    private static final String[] RANDOM_IMGS_CONCERTO = {
"https://i.postimg.cc/GprjT3Lc/spettacolo1.png",
"https://i.postimg.cc/pdZJfNrK/spettacolo2.png",
"https://i.postimg.cc/6pC0Q4Nj/spettacolo3.png",
"https://i.postimg.cc/k4gFwjRz/spettacolo4.png",
"https://i.postimg.cc/VvS9PdRF/spettacolo5.png",
"https://i.postimg.cc/wxPccQq4/spettacolo6.png",
"https://i.postimg.cc/4yr6fs2R/spettacolo7.png"
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
        } else if (categoryId == 9 ) {
            int index = random.nextInt(RANDOM_IMGS_CONCERTO.length);
            return RANDOM_IMGS_CONCERTO[index];
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

        formEvent.setMainImage(chooseRandomImage(formEvent.getCategory().getId()));

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
