package by.training.machine.monitoring.dao;

import java.util.List;

public interface CRUDDao<ENTITY, KEY> {
    KEY save(ENTITY entity) throws DaoException;
    boolean update(ENTITY entity) throws DaoException;
    boolean delete(ENTITY entity) throws DaoException;
    ENTITY getById(KEY id) throws DaoException;
    List<ENTITY> findAll() throws DaoException;
}
