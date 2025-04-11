package org.java.letsplay.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event {

    // states
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci un nome valido")
    @Size(max = 25, message = "Il nome dell'evento può essere lungo massimo 20 caratteri")
    private String name;

    private Integer price;

    @Lob
    @NotBlank(message = "Inserisci una descrizione valida")
    private String description;

    @Lob
    private String mainImage;
    @Lob
    private String image;

    @Lob
    @NotBlank(message = "Inserisci un indirizzo valido")
    private String address;

    @NotNull(message = "Inserisci una data valida")
    private LocalDate startDate;

    private LocalDate endDate;
    private LocalDate registrationClosingDate;
    private LocalDate registrationOpeningDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


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
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public LocalDate getRegistrationClosingDate() {
        return this.registrationClosingDate;
    }
    public void setRegistrationClosingDate(LocalDate registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }
    public LocalDate getRegistrationOpeningDate() {
        return this.registrationOpeningDate;
    }
    public void setRegistrationOpeningDate(LocalDate registrationOpeningDate) {
        this.registrationOpeningDate = registrationOpeningDate;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getPrice() {
        return this.price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getMainImage() {
        return this.mainImage;
    }
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }


}
