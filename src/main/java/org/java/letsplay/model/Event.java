package org.java.letsplay.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci un nome valido")
    private String name;

    @Lob
    private String description;

    @Lob
    @NotBlank(message = "Inserisci un indirizzo valido")
    private String address;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @NotNull(message = "Inserire aperte o chiuse")
    private Boolean isRegistrationOpen = true;

    private LocalDate registrationClosingDate;
    
    @ManyToMany
    @JoinTable(
        name = "event_team",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;


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
    public List<Team> getTeams() {
        return this.teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    public Boolean isIsRegistrationOpen() {
        return this.isRegistrationOpen;
    }
    public Boolean getIsRegistrationOpen() {
        return this.isRegistrationOpen;
    }
    public void setIsRegistrationOpen(Boolean isRegistrationOpen) {
        this.isRegistrationOpen = isRegistrationOpen;
    }
    public LocalDate getRegistrationClosingDate() {
        return this.registrationClosingDate;
    }
    public void setRegistrationClosingDate(LocalDate registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

}
