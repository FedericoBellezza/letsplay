package org.java.letsplay.controller;

import org.java.letsplay.model.Category;
import org.java.letsplay.model.Event;
import org.java.letsplay.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryService.findAll());

        return "category/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("edit", false);

        return "category/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,  Model model){

        if (bindingResult.hasErrors()) {
            return "event/create-or-edit";
        }

        categoryService.save(formCategory);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,  Model model) {
        model.addAttribute("edit", true);
        model.addAttribute("category", categoryService.getById(id));

        return "category/create-or-edit";
    }

   @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("category") Category formCategory, Model model, BindingResult bindingResult, @PathVariable Integer id) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("category", categoryService.getById(id));
            return "event/create-or-edit";
        }

        categoryService.save(formCategory);        
        return "redirect:/categories";
    }

}
