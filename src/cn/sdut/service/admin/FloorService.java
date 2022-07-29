package cn.sdut.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Floor;

/**
 * Â¥²ãservice
 *
 */
@Service
public interface FloorService {
	int add(Floor floor);
	int edit(Floor floor);
	int delete(Long id);
	List<Floor> findList(Map<String, Object> queryMap);
	List<Floor> findAll();
	Integer getTotal(Map<String, Object> queryMap);
}
