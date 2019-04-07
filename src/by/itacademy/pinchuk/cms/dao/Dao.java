package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.util.DaoFilter;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    public Optional<T> get(int id);

    public Optional<T> get(int id, boolean onlyPublished);

    public List<T> getAll(DaoFilter.Builder filter);

    public List<T> getAll();

    public Optional<T> create(T entity);

    public boolean update(T entity);

    public boolean delete(int id);
}
