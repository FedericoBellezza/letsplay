package org.java.letsplay.service;

import java.util.ArrayList;
import java.util.List;
import org.java.letsplay.model.Category;
import org.java.letsplay.model.CategoryForm;
import org.java.letsplay.model.Event;
import org.java.letsplay.model.Image;
import org.java.letsplay.repository.CategoryRepository;
import org.java.letsplay.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    // services
    @Autowired
    private EventService eventService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    // methods
    public List<Category> findAll(){
        return categoryRepository.findAll(Sort.by("name"));
    }   
    public Category getById(Integer id){
        return categoryRepository.findById(id).get();
    }
    public void save(Category category){
        categoryRepository.save(category);
    }
    public void deleteById(Integer id){
        Category category = categoryRepository.findById(id).get();
        List<Image> imagesToDelete = new ArrayList<>(category.getImages());
        category.getImages().clear();
        imageRepository.deleteAll(imagesToDelete);
        List<Event> eventsToDelete = new ArrayList<>(category.getEvents());
        eventService.deleteAll(eventsToDelete);

        categoryRepository.deleteById(id);
    }
    public void createCategoryWithImages(CategoryForm form) {
        Category category = new Category();
        category.setName(form.getName());
        
        category = categoryRepository.save(category);
        
        for (String url : form.getImageUrls()) {
            if (url != null && !url.trim().isEmpty()) {
                Image image = new Image();
                image.setUrl(url.trim());
                image.setCategory(category);
                image = imageRepository.save(image);
            }
        }
        
        categoryRepository.save(category);
    }
    public void updateCategoryWithImages(CategoryForm form, Integer id) {
        Category category = categoryRepository.findById(id).get();
        category.setName(form.getName());
        
        category = categoryRepository.save(category);

        List<Image> imagesToDelete = new ArrayList<>(category.getImages());
        category.getImages().clear();
        imageRepository.deleteAll(imagesToDelete);
        
        for (String url : form.getImageUrls()) {
            if (url != null && !url.trim().isEmpty()) {
                Image image = new Image();
                image.setUrl(url.trim());
                image.setCategory(category);
                image = imageRepository.save(image);
            }
        }
        
        categoryRepository.save(category);
    }
    public void duplicateCategory(Category category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        categoryRepository.save(newCategory);

        for (Image image : category.getImages()) {
            Image newImage = new Image();
            newImage.setUrl(image.getUrl());
            newImage.setCategory(newCategory);
            imageRepository.save(newImage);
        }
        
        categoryRepository.save(newCategory);
    }
}
