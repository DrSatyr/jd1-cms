package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.Dto;
import by.itacademy.pinchuk.cms.entity.Entity;

public interface Mapper<E extends Entity, D extends Dto> {

    D entityToDto(E entity);

    E dtoToEntity(D dao);
}
