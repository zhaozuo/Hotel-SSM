package cn.sdut.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sdut.dao.admin.FloorDao;
import cn.sdut.entity.admin.Floor;
import cn.sdut.service.admin.FloorService;
@Service
public class FloorServiceImpl implements FloorService {

	@Autowired
	private FloorDao floorDao;
	
	@Override
	public int add(Floor floor) {
		return floorDao.add(floor);
	}

	@Override
	public int edit(Floor floor) {
		return floorDao.edit(floor);
	}

	@Override
	public int delete(Long id) {
		return floorDao.delete(id);
	}

	@Override
	public List<Floor> findList(Map<String, Object> queryMap) {
		return floorDao.findList(queryMap);
	}

	@Override
	public List<Floor> findAll() {
		return floorDao.findAll();
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		return floorDao.getTotal(queryMap);
	}

}
