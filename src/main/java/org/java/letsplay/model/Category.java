package org.java.letsplay.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
public class Category {

    // states
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci un nome valido")
    private String name;
    
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Event> events;

    @OneToMany(mappedBy = "category")
    private List<Image> images = new ArrayList<Image>();


    // getter and setter
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Event> getEvents() {
        return this.events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public List<Image> getImages() {
        return this.images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }
    public Image getImageByIndex(int id){
    return images.get(id);
}
    

    // to string
    @Override
    public String toString() {
        return name;
    }
}
