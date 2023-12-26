package com.openclassrooms.rentals.services;

import com.openclassrooms.rentals.dto.MessageDTO;
import com.openclassrooms.rentals.models.Message;
import com.openclassrooms.rentals.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setUserId(messageDTO.getUserId());
        message.setRentalId(messageDTO.getRentalId());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }
}