package org.hmcampoverde.service;

import java.util.List;

import org.hmcampoverde.message.Message;

public interface Service<T> {

    public List<T> findAll();

    public T findById(Long id);

    public Message create(T entity);

    public Message update(Long id, T entity);

    public Message delete(Long id);

    default boolean isNew(Long id) {

        if (id == null)
            return true;

        if (id instanceof Number) {
            return ((Number) id).longValue() == 0L;
        }

        return false;
    }

}
