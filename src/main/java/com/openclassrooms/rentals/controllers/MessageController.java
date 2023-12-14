package com.openclassrooms.rentals.controllers;

import com.openclassrooms.rentals.dto.MessageDTO;
import com.openclassrooms.rentals.models.Message;
import com.openclassrooms.rentals.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            Message message = messageService.createMessage(messageDTO);
            MessageDTO responseDTO = new MessageDTO();
            responseDTO.setId(message.getId());
            responseDTO.setMessage(message.getMessage());
            responseDTO.setUserId(message.getUserId());
            responseDTO.setRentalId(message.getRentalId());
            responseDTO.setCreatedAt(message.getCreatedAt());
            responseDTO.setUpdatedAt(message.getUpdatedAt());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}