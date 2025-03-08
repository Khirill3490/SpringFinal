package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.finalspring.entity.Hotel;
import org.example.finalspring.entity.Photo;
import org.example.finalspring.exception.EntityAlreadyExistsException;
import org.example.finalspring.exception.EntityNotFoundException;
import org.example.finalspring.mapper.hotel.HotelMapper;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.hotel.HotelFilterRequest;
import org.example.finalspring.model.request.hotel.MarkRequest;
import org.example.finalspring.model.response.hotel.HotelResponseList;
import org.example.finalspring.repository.HotelRepository;
import org.example.finalspring.service.HotelService;
import org.example.finalspring.service.PhotoService;
import org.example.finalspring.specification.HotelSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelSpecification hotelSpecification;
    private final PhotoService photoService;

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ошибка. Отель с id " + id + " не найден"));
    }

    @Override
    public Hotel save(Hotel hotel) {
        if (existsByName(hotel.getName())) {
            throw new EntityAlreadyExistsException("Отель с таким названием уже существует");
        }
        hotel.setRating(0.0);
        hotel.setNumberOfRating(0);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Long id, Hotel hotel) {
        Hotel hotelToUpdate = findById(id);

        if (!hotel.getName().equals(hotelToUpdate.getName()) && existsByName(hotel.getName())) {
            throw new EntityAlreadyExistsException("Отель с таким названием уже существует");
        }

        hotelToUpdate.setName(hotel.getName());
        hotelToUpdate.setTitle(hotel.getTitle());
        hotelToUpdate.setCity(hotel.getCity());
        hotelToUpdate.setAddress(hotel.getAddress());
        hotelToUpdate.setDistanceFromCenter(hotel.getDistanceFromCenter());

        return hotelRepository.save(hotelToUpdate);
    }

    @Override
    public void delete(Long id) {
        Hotel hotel = findById(id);
        hotelRepository.delete(hotel);
    }

    @SneakyThrows
    @Override
    public Hotel uploadPhoto(MultipartFile file, Long hotelId) {
            Hotel hotel = findById(hotelId);
            Photo photo = photoService.uploadPhoto(file, hotel);

            hotel.getPhotos().add(photo);

            return hotelRepository.save(hotel);
    }

    @Override
    public Hotel addRating(Long id, MarkRequest request) {
        Hotel hotel = findById(id);

        if (hotel.getRating() < 1.0) {
            hotel.setRating(request.getNewMark().doubleValue());
            hotel.setNumberOfRating(1);

            return hotelRepository.save(hotel);
        }

        var totalRating = hotel.getRating() * hotel.getNumberOfRating();
        totalRating = totalRating - hotel.getRating() + request.getNewMark();
        var rating = totalRating / hotel.getNumberOfRating();
        var roundedRating = Math.round(rating * 1000.0) / 1000.0;

        hotel.setRating(roundedRating);
        hotel.setNumberOfRating(hotel.getNumberOfRating() + 1);

        return hotelRepository.save(hotel);
    }

    public HotelResponseList filterHotels(HotelFilterRequest filterRequest, PaginationRequest pagination) {
        Specification<Hotel> specification = Specification.where(hotelSpecification.filterById(filterRequest.getId()))
                .and(hotelSpecification.filterByName(filterRequest.getName()))
                .and(hotelSpecification.filterByTitle(filterRequest.getTitle()))
                .and(hotelSpecification.filterByCity(filterRequest.getCity()))
                .and(hotelSpecification.filterByAddress(filterRequest.getAddress()))
                .and(hotelSpecification.filterByDistanceFromCenter(filterRequest.getDistanceFromCenter()))
                .and(hotelSpecification.filterByRating(filterRequest.getRating()))
                .and(hotelSpecification.filterByNumberOfRating(filterRequest.getNumberOfRating()));

        PageRequest pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        Page<Hotel> hotelsPage = hotelRepository.findAll(specification, pageRequest);

        return new HotelResponseList(hotelsPage.getContent()
                .stream()
                .map(hotelMapper::toResponse)
                .toList(), hotelsPage.getTotalElements());
    }

    private boolean existsByName(String hotelName) {
        return hotelRepository.existsByName(hotelName);
    }
}
