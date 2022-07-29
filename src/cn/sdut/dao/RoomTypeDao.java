package cn.sdut.dao;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import cn.sdut.entity.RoomType;

@Repository
public interface RoomTypeDao {
	int add(RoomType roomType);
	int edit(RoomType roomType);
	int delete(Long id);
	List<RoomType> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	List<RoomType> findAll();
	RoomType find(Long id);
	int updateNum(RoomType roomType);
}
