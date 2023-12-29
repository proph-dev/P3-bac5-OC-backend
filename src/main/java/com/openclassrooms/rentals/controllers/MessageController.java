package com.openclassrooms.rentals.controllers;

import com.openclassrooms.rentals.dto.MessageDTO;
import com.openclassrooms.rentals.models.Message;
import com.openclassrooms.rentals.services.MessageService;
import com.openclassrooms.rentals.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Long userId = userService.getUserIdByEmail(userEmail);

            messageDTO.setUserId(userId);

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