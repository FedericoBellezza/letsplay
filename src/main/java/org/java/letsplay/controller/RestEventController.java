package org.java.letsplay.controller;

import java.util.List;
import java.util.Optional;
import org.java.letsplay.model.Event;
import org.java.letsplay.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/events")
public class RestEventController {

    @Autowired
    private EventService eventService;

    // routes    
    @GetMapping
    public List<Event> index(Model model){
        return eventService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Event> show(@PathVariable Integer id, Model model){

        // gestisco il not_found
        Optional<Event> eventToFind = eventService.findById(id);
        if (eventToFind.isEmpty()) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Event>(eventToFind.get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> store(@Valid @RequestBody Event event){
        return new ResponseEntity<Event>(eventService.create(event), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Event> update(@Valid @RequestBody Event event, @PathVariable Integer id) {

        // gestisco il not_found
        Optional<Event> eventToFind = eventService.findById(id);
        if (eventToFind.isEmpty()) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        eventService.update(event);        
        return new ResponseEntity<Event>(eventToFind.get(), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Event> delete(@PathVariable Integer id) {

        // gestisco il not_found
        Optional<Event> eventToFind = eventService.findById(id);
        if (eventToFind.isEmpty()) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        eventService.deleteById(id);
        return new ResponseEntity<Event>(HttpStatus.OK);
    }    
}
