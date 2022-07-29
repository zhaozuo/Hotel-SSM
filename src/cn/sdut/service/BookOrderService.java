package cn.sdut.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.BookOrder;

@Service
public interface BookOrderService {
	int add(BookOrder bookOrder);
	int edit(BookOrder bookOrder);
	int delete(Long id);
	List<BookOrder> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	BookOrder find(Long id);
}
