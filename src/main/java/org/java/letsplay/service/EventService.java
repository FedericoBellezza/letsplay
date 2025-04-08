package org.java.letsplay.service;

import java.util.List;
import java.util.Optional;
import org.java.letsplay.model.Category;
import org.java.letsplay.model.Event;
import org.java.letsplay.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll(){
        return eventRepository.findAll();
    }
    public List<Event> findAllSorted(String sort){
        return eventRepository.findAll(Sort.by(sort));
    }

    public Optional<Event> findById(Integer id){
        return eventRepository.findById(id);
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

    // advanced search
    public List<Event> advancedSearch(String name, Category category, String address ){
        return eventRepository.findByNameContainingAndCategoryAndAddressContaining(name, category, address);
    }

    public List<Event> advancedSearchNoCategory(String name, String address ){
        return eventRepository.findByNameContainingAndAddressContaining(name, address);
    }

    public Event create(Event event){
        return eventRepository.save(event);
    }
    
    public Event update(Event event){
       return eventRepository.save(event);
    }

}
