package org.java.letsplay.controller;

import java.util.ArrayList;
import java.util.List;
import org.java.letsplay.model.CategoryForm;
import org.java.letsplay.model.Image;
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
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "category/create-or-edit";
    }
    
    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryForm categoryForm) {
        categoryService.createCategoryWithImages(categoryForm);
        return "redirect:/categories";
    }

    // @GetMapping("/create")
    // public String create(Model model){
    //     model.addAttribute("category", new Category());
    //     model.addAttribute("edit", false);

    //     return "category/create-or-edit";
    // }

    // @PostMapping("/create")
    // public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,  Model model){

    //     if (bindingResult.hasErrors()) {
    //         return "event/create-or-edit";
    //     }

    //     categoryService.save(formCategory);
    //     return "redirect:/categories";
    // }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,  Model model) {
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName(categoryService.getById(id).getName());
        List<String> urlList = new ArrayList<String>();
        List<Image> imageList = categoryService.getById(id).getImages();

        for (Image image : imageList){
            urlList.add(image.getUrl());
        }

        categoryForm.setImageUrls(urlList);
        
        
        model.addAttribute("edit", true);
        model.addAttribute("categoryForm", categoryForm);
        model.addAttribute("category", categoryService.getById(id));

        return "category/create-or-edit";
    }

   @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute CategoryForm categoryForm,  BindingResult bindingResult, Model model, @PathVariable Integer id) {

        if (bindingResult.hasErrors()) {
            CategoryForm newCategoryForm = new CategoryForm();
            newCategoryForm.setName(categoryService.getById(id).getName());
            List<String> urlList = new ArrayList<String>();
            List<Image> imageList = categoryService.getById(id).getImages();

            for (Image image : imageList){
                urlList.add(image.getUrl());
            }

            categoryForm.setImageUrls(urlList);
            model.addAttribute("edit", true);
            model.addAttribute("categoryForm", categoryForm);
            model.addAttribute("category", categoryService.getById(id));
            return "event/create-or-edit";
        }

        categoryService.updateCategoryWithImages(categoryForm, id);        
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

    @PostMapping("/duplicate/{id}")
    public String duplicateCategory(@PathVariable Integer id) {
        categoryService.duplicateCategory(categoryService.getById(id));
        return "redirect:/categories";
    }
    
}
