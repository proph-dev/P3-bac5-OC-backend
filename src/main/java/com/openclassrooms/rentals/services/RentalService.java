package com.openclassrooms.rentals.services;

import com.openclassrooms.rentals.dto.RentalDTO;
import com.openclassrooms.rentals.models.Rental;
import com.openclassrooms.rentals.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    // Trouver tous les rentals
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    // Trouver un rental par son ID
    public Rental findById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    // Créer un nouveau rental
    public Rental createRental(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        // Initialiser rental avec les données de rentalDTO
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(rentalDTO.getOwnerId());
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

    // Mettre à jour un rental existant
    public Rental updateRental(Long id, RentalDTO rentalDTO) {
        Rental rental = findById(id);
        // Mettre à jour les propriétés de rental avec celles de rentalDTO
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }
}