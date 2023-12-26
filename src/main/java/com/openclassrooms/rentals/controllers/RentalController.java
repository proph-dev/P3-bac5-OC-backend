package com.openclassrooms.rentals.controllers;

import com.openclassrooms.rentals.dto.RentalCreateDTO;
import com.openclassrooms.rentals.dto.RentalDTO;
import com.openclassrooms.rentals.dto.RentalResponse;
import com.openclassrooms.rentals.models.Rental;
import com.openclassrooms.rentals.security.JwtTokenProvider;
import com.openclassrooms.rentals.services.RentalService;
import com.openclassrooms.rentals.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.rentals.services.FileStorageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private UserService userService;


    // GET all rentals
    @GetMapping
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<Rental> rentals = rentalService.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        RentalResponse rentalResponse = new RentalResponse(rentalDTOs);
        return ResponseEntity.ok(rentalResponse);
    }

    // GET a single rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.findById(id);
        RentalDTO rentalDTO = convertToDto(rental);
        return ResponseEntity.ok(rentalDTO);
    }

    // POST a new rental
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalDTO> createRental (@ModelAttribute RentalCreateDTO rentalDTO, 
                                                    @RequestHeader("Authorization") String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String userEmail = jwtTokenProvider.getEmailFromToken(token);
        Long userId = userService.getUserIdByEmail(userEmail);
        rentalDTO.setOwnerId(userId);

        String fileName = fileStorageService.storeFile(rentalDTO.getPicture());

        Rental rental = rentalService.createRental(rentalDTO, fileName);
        RentalDTO createdRentalDTO = convertToDto(rental);
        return ResponseEntity.ok(createdRentalDTO);
    }

    // PUT (update) a rental
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @ModelAttribute RentalDTO rentalDTO) {
        Rental rental = rentalService.findById(id);

        // Mettre à jour les propriétés de rental avec celles de rentalDTO
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        // Vérifier s'il y a une nouvelle image à télécharger
        if (rentalDTO.getNewPicture() != null && !rentalDTO.getNewPicture().isEmpty()) {
            String newFileName = fileStorageService.storeFile(rentalDTO.getNewPicture());
            rental.setPicture(newFileName);
        }

        Rental updatedRental = rentalService.updateRental(id, rentalDTO);
        RentalDTO updatedRentalDTO = convertToDto(updatedRental);
        return ResponseEntity.ok(updatedRentalDTO);
    }

    // Méthode privée pour convertir une entité Rental en RentalDTO
    private RentalDTO convertToDto(Rental rental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(rental.getId());
        rentalDTO.setName(rental.getName());
        rentalDTO.setSurface(rental.getSurface());
        rentalDTO.setPrice(rental.getPrice());
        rentalDTO.setPicture(rental.getPicture());
        rentalDTO.setDescription(rental.getDescription());
        rentalDTO.setOwnerId(rental.getOwnerId());
        rentalDTO.setCreatedAt(rental.getCreatedAt());
        rentalDTO.setUpdatedAt(rental.getUpdatedAt());
        return rentalDTO;
    }
}