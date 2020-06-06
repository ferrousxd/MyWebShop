package repositories.interfaces;

import java.util.List;

public interface IEntityRepository<T> {
    void add(T entity);
    void update(T entity);
    void remove(T entity);
    T queryOne(String sql);
    List<T> queryTwo(String sql);
    List<T> queryThree(String sql);
}
