package repositories.interfaces;

import domain.Product;

import java.util.List;

public interface IEntityRepository<T> {
    void add(T entity);
    void update(T entity);
    void remove(T entity);
    T queryOne(String sql);
    List<T> queryTwo(String sql);
}
