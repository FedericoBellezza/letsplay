package org.java.letsplay.repository;

import org.java.letsplay.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    
}
