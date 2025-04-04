package org.java.letsplay.service;

import java.util.List;

import org.java.letsplay.model.Event;
import org.java.letsplay.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public Event getById(Integer id){
        return eventRepository.findById(id).get();
    }

    public void save(Event event){
        eventRepository.save(event);
    }

    public void deleteById(Integer id){
        eventRepository.deleteById(id);
    }
    
}
