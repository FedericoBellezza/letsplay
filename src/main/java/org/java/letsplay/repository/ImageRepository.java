package org.java.letsplay.repository;

import org.java.letsplay.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    
}
