package com.openclassrooms.rentals.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    // Chemin du répertoire de stockage des fichiers, défini dans application.properties
    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    // Méthode pour stocker le fichier
    public String storeFile(MultipartFile file) {
        // Normalise le nom du fichier
        String fileName = cleanPath(file.getOriginalFilename());

        try {
            // Vérification pour empêcher le stockage de fichiers avec des chemins relatifs malveillants
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copie le fichier au lieu de stockage, en remplaçant le même nom de fichier s'il existe déjà
            Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    // Méthode personnalisée pour nettoyer le chemin du fichier
    private String cleanPath(String path) {
        return path.replaceAll("[.]+", "");
    }
}

// Exception personnalisée pour le stockage de fichiers
class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}