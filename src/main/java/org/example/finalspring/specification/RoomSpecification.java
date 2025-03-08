package org.example.finalspring.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.example.finalspring.entity.Room;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RoomSpecification {

    public Specification<Room> filterById(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    public Specification<Room> filterByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public Specification<Room> filterByPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (minPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.ge(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.le(root.get("price"), maxPrice));
            }

            return predicate;
        };
    }

    public Specification<Room> filterByGuestCapacity(Integer capacity) {
        return (root, query, criteriaBuilder) -> {
            if (capacity == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("maxOccupancy"), capacity);
        };
    }

    public Specification<Room> filterByCheckInOutDates(LocalDate checkIn, LocalDate checkOut) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            var subquery = query.subquery(LocalDate.class);
            var subRoot = subquery.from(Room.class);

            subquery.select(subRoot.join("unavailableDates", JoinType.INNER))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get("id"), root.get("id")),
                            criteriaBuilder.between(subRoot.join("unavailableDates"), checkIn, checkOut)
                    ));

            return criteriaBuilder.not(criteriaBuilder.exists(subquery));
        };
    }

    public Specification<Room> filterByHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }
}
