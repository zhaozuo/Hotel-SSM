package cn.sdut.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Room;

@Service
public interface RoomService {
	int add(Room room);
	int edit(Room room);
	int delete(Long id);
	List<Room> findList(Map<String, Object> queryMap);
	List<Room> findAll();
	Integer getTotal(Map<String, Object> queryMap);
	Room find(Long id);
	Room findBySn(String sn);
}
