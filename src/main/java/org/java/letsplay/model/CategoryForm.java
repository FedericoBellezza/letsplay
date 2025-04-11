package org.java.letsplay.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

public class CategoryForm {

    // states
    @NotBlank(message = "Inserisci un nome valido")
    private String name;
    private List<String> imageUrls = new ArrayList<String>();
    

    // getter e setter
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getImageUrls() {
        return this.imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
}

