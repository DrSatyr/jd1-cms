package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Entity;
import lombok.SneakyThrows;

import java.util.Optional;

public abstract class BaseDao<E extends Entity> {

    public abstract Optional<E> get(int id, boolean onlyPublished);

    @SneakyThrows
    public Optional<E> get(int id) {
        return get(id, false);
    }
}