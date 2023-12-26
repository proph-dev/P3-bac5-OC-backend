package com.openclassrooms.rentals.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rentals.dto.UserResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadController {

  @Value("${file.upload-dir}")
  private String fileUploadDirectory;

  @GetMapping("/backend/img/{fileName:.+}")
  @ApiOperation(value = "Uplodad image")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Image uploaded successfully", response = UserResponse.class),
    @ApiResponse(code = 400, message = "Image to big, or incorrect extension", response = String.class),
    @ApiResponse(code = 401, message = "Incorrect token", response = String.class)
  })
  public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
    try {
      String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

      Path filePath = Paths.get(fileUploadDirectory).resolve(decodedFileName);
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        return ResponseEntity.ok().body(resource);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (MalformedURLException e) {
      return ResponseEntity.notFound().build();
    }
  }
}