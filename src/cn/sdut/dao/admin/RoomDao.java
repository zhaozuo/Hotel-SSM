package cn.sdut.dao.admin;
import java.util.List;
import java.util.Map;


/**
 * ����dao
 */
import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Room;

@Repository
public interface RoomDao {
	int add(Room room);
	int edit(Room room);
	int delete(Long id);
	List<Room> findList(Map<String, Object> queryMap);
	List<Room> findAll();
	Integer getTotal(Map<String, Object> queryMap);
	Room find(Long id);
	Room findBySn(String sn);
}
