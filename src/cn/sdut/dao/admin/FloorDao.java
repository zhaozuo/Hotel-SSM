package cn.sdut.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Floor;

/**
 * Â¥²ãdao
 */
@Repository
public interface FloorDao {
	int add(Floor floor);
	int edit(Floor floor);
	int delete(Long id);
	List<Floor> findList(Map<String, Object> queryMap);
	List<Floor> findAll();
	Integer getTotal(Map<String, Object> queryMap);
}
