package org.example.finalspring.mapper.photo;

import org.example.finalspring.entity.Photo;
import org.example.finalspring.model.response.photo.PhotoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoResponse toResponse(Photo photo);
}
