package cn.sdut.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sdut.dao.admin.RoomDao;
import cn.sdut.entity.admin.Room;
import cn.sdut.service.admin.RoomService;
@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDao roomDao;
	
	@Override
	public int add(Room room) {
		return roomDao.add(room);
	}

	@Override
	public int edit(Room room) {
		return roomDao.edit(room);
	}

	@Override
	public int delete(Long id) {
		return roomDao.delete(id);
	}

	@Override
	public List<Room> findList(Map<String, Object> queryMap) {
		return roomDao.findList(queryMap);
	}

	@Override
	public List<Room> findAll() {
		return roomDao.findAll();
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		return roomDao.getTotal(queryMap);
	}

	@Override
	public Room find(Long id) {
		return roomDao.find(id);
	}

	@Override
	public Room findBySn(String sn) {
		return roomDao.findBySn(sn);
	}

}
