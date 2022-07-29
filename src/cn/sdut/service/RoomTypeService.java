package cn.sdut.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.RoomType;

@Service
public interface RoomTypeService {
	int add(RoomType roomType);
	int edit(RoomType roomType);
	int delete(Long id);
	List<RoomType> findList(Map<String, Object> queryMap);
	List<RoomType> findAll();
	Integer getTotal(Map<String, Object> queryMap);
	RoomType find(Long id);
	int updateNum(RoomType roomType);
}
