package fa.training.dao;

import java.util.List;

public interface CommonDAO<T> {
    List<T> findAll();
    T findById(int id);
    void update(T element);
    void delete(int id);
}
