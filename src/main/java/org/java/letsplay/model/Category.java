package org.java.letsplay.model;

import java.util.List;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci un nome valido")
    private String name;

    private String subcategory;

    @OneToMany(mappedBy = "category")
    private List<Event> events;


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
    public String getSubcategory() {
        return this.subcategory;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public List<Event> getEvents() {
        return this.events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }


    @Override
    public String toString() {
        return name;
    }
}
