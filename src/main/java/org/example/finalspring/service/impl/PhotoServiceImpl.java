package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Hotel;
import org.example.finalspring.entity.Photo;
import org.example.finalspring.exception.IncorrectDataException;
import org.example.finalspring.repository.HotelRepository;
import org.example.finalspring.repository.PhotoRepository;
import org.example.finalspring.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${spring.app.photosPath}")
    private String photoDir;

    public byte[] getPhoto(String fileName) throws IOException {
        File file = new File(photoDir + "/" + fileName);
        return java.nio.file.Files.readAllBytes(file.toPath());
    }


    @Override
    public Photo uploadPhoto(MultipartFile file, Hotel hotel) throws IOException {
        createFolder();

        String filename = file.getOriginalFilename();
        String uniqueFileName;


        if (filename != null && !filename.isEmpty() && filename.lastIndexOf(".") != -1) {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            uniqueFileName = UUID.randomUUID().toString() + extension;
        } else {
            throw new IncorrectDataException("Некорректное название/разрешение файла");
        }

        File uploadedFile = new File(photoDir + "/" + uniqueFileName);

        try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
            fos.write(file.getBytes());
        }

        Photo photo = new Photo();
        photo.setFilename(uniqueFileName);
        photo.setHotel(hotel);

        return photoRepository.save(photo);
    }

    private void createFolder() {
        Path directoryPath = Paths.get(photoDir);

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
