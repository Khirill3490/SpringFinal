package org.example.finalspring.service.impl;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.mongo.RoomsBooking;
import org.example.finalspring.entity.mongo.UsersRegistration;
import org.example.finalspring.repository.mongoRep.RoomsBookingRepository;
import org.example.finalspring.repository.mongoRep.UsersRegistrationRepository;
import org.example.finalspring.service.StatisticService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final UsersRegistrationRepository usersRegistrationRepository;
    private final RoomsBookingRepository roomsBookingRepository;

    @Value("${spring.app.exportPath}")
    private String folderPath;

    @Value("${spring.app.fileName}")
    private String fileName;

    public String exportToCSV() {
        String filePath = folderPath + "/" + fileName;

        createFolder();

        writeIntoCSV(filePath);

        return filePath;
    }

    private void writeIntoCSV(String filePath) {
        List<UsersRegistration> userList = usersRegistrationRepository.findAll();
        List<RoomsBooking> bookingsList = roomsBookingRepository.findAll();

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {

            String[] userHeader = {"№", "User ID", "Registration Date"};
            writer.writeNext(userHeader);

            int i = 0;

            for (UsersRegistration userRegistration : userList) {
                String[] userData = {
                        String.valueOf(++i),
                        String.valueOf(userRegistration.getUserId()),
                        userRegistration.getRegistrationDate().toString()
                };
                writer.writeNext(userData);
            }

            writer.writeNext(new String[] {});

            String[] roomHeader = {"№", "User ID", "Check-In Date", "Check-Out Date", "Booking Date"};
            writer.writeNext(roomHeader);

            i = 0;

            for (RoomsBooking roomBooking : bookingsList) {
                String[] roomData = {
                        String.valueOf(++i),
                        String.valueOf(roomBooking.getUserId()),
                        roomBooking.getCheckInDate().toString(),
                        roomBooking.getCheckOutDate().toString(),
                        roomBooking.getBookingDate().toString()
                };
                writer.writeNext(roomData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createFolder() {
        Path directoryPath = Paths.get(folderPath);

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
