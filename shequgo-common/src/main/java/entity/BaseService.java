package entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Colin
 * @Date: 2019/3/23 13:59
 */
public abstract class BaseService<T,R extends JpaRepository<T,Integer>>{

    protected R repo;

    public BaseService(R repo) {
        this.repo = repo;
    }

    public T findById(Integer id){
        Optional<T> opt =  repo.findById(id);
        return opt.orElse(null);
    }

    public T save(T entity){
        return repo.save(entity);
    }

    public void delete(T entity){
        repo.delete(entity);
    }

    public void deleteById(Integer id){
        repo.deleteById(id);
    }

    public List<T> findAll(){
        return repo.findAll();
    }

}
