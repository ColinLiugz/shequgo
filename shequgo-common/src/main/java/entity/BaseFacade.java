package entity;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/23 13:58
 */
public interface BaseFacade<T extends BaseObject> {

    T findById(Integer id);

    T save(T entity);

    void delete(T entity);

    void deleteById(Integer id);

    List<T> findAll();
}
