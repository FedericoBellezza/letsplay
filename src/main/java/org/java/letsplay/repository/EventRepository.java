package org.java.letsplay.repository;

import java.util.List;
import org.java.letsplay.model.Category;
import org.java.letsplay.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameContainingAndCategoryAndAddressContaining(String name, Category category, String address);
    List<Event> findByNameContainingAndAddressContaining(String name, String address);
}
