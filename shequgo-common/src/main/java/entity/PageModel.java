package entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/4/7 15:49
 */
@Data
public class PageModel<T> implements Serializable {

    public PageModel(){
        this.content = new ArrayList<>();
        this.totalElements = 0;
    }

    public PageModel(long totalElements,List<T> content){
        this.totalElements = totalElements;
        this.content = content;
    }

    private List<T> content;
    private long totalElements;
}
