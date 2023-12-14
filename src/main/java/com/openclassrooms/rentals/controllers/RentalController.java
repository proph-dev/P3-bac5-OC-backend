package com.openclassrooms.rentals.controllers;

import com.openclassrooms.rentals.dto.RentalDTO;
import com.openclassrooms.rentals.models.Rental;
import com.openclassrooms.rentals.security.JwtTokenFilter;
import com.openclassrooms.rentals.security.JwtTokenProvider;
import com.openclassrooms.rentals.services.RentalService;
import com.openclassrooms.rentals.services.UserService;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.openclassrooms.rentals.services.FileStorageService;

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
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        List<Rental> rentals = rentalService.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
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
    public ResponseEntity<RentalDTO> createRental (@ModelAttribute RentalDTO rentalDTO, 
                                                    @RequestHeader("Authorization") String token) {
        
        String userEmail = jwtTokenProvider.getEmailFromToken(token);
        Long userId = userService.getUserIdByEmail(userEmail);

        rentalDTO.setOwnerId(userId);

        System.out.println(userEmail);
        // String fileName = fileStorageService.storeFile(file);

        // rentalDTO.setPicture(fileDownloadUri);

        Rental rental = rentalService.createRental(rentalDTO);
        RentalDTO createdRentalDTO = convertToDto(rental);
        return ResponseEntity.ok(createdRentalDTO);
    }

    // PUT (update) a rental
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
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
        //rentalDTO.setPicture(rental.getPicture());
        rentalDTO.setDescription(rental.getDescription());
        rentalDTO.setOwnerId(rental.getOwnerId());
        rentalDTO.setCreatedAt(rental.getCreatedAt());
        rentalDTO.setUpdatedAt(rental.getUpdatedAt());
        return rentalDTO;
    }
}