package org.example.finalspring.service;

import org.example.finalspring.entity.Hotel;
import org.example.finalspring.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    byte[] getPhoto(String fileName) throws IOException;

    Photo uploadPhoto(MultipartFile file, Hotel hotel) throws IOException;
}
