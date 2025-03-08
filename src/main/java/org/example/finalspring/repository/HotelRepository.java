package org.example.finalspring.repository;

import org.example.finalspring.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    Page<Hotel> findAll(Pageable pageable);

    Optional<Hotel> findByName(String name);

    boolean existsByName(String name);
}
