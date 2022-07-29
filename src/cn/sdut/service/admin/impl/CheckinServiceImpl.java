package cn.sdut.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sdut.dao.admin.CheckinDao;
import cn.sdut.entity.admin.Checkin;
import cn.sdut.service.admin.CheckinService;
@Service
public class CheckinServiceImpl implements CheckinService {

	@Autowired
	private CheckinDao checkinDao;

	@Override
	public int add(Checkin checkin) {
		return checkinDao.add(checkin);
	}

	@Override
	public int edit(Checkin checkin) {
		return checkinDao.edit(checkin);
	}

	@Override
	public int delete(Long id) {
		return checkinDao.delete(id);
	}

	@Override
	public List<Checkin> findList(Map<String, Object> queryMap) {
		return checkinDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		return checkinDao.getTotal(queryMap);
	}

	@Override
	public Checkin find(Long id) {
		return checkinDao.find(id);
	}

	@Override
	public List<Map> getStatsByMonth() {
		return checkinDao.getStatsByMonth();
	}

	@Override
	public List<Map> getStatsByDay() {
		return checkinDao.getStatsByDay();
	}

	

	
	
	

}
