package com.openclassrooms.rentals.repository;

import com.openclassrooms.rentals.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}