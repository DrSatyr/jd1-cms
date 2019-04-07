package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dto.Dto;
import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.util.DaoFilter;

import java.util.List;
import java.util.Optional;

public interface Service<E extends Entity> {

    Optional<? extends Dto> get(int id);

    Optional<? extends Dto> get(int id, boolean onlyPublished);

    List<? extends Dto> getAll(DaoFilter.Builder filter);

    List<? extends Dto> getAll();

    List<? extends Dto> getAllPublished();

    Optional<E> create(E entity);

    boolean update(E entity);

    boolean delete(int id);
}
