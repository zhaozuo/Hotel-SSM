package cn.sdut.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.BookOrder;

@Repository
public interface BookOrderDao {
	int add(BookOrder bookOrder);
	int edit(BookOrder bookOrder);
	int delete(Long id);
	List<BookOrder> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	BookOrder find(Long id);
}
