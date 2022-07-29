package cn.sdut.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Checkin;

@Service
public interface CheckinService {
	int add(Checkin checkin);
	int edit(Checkin checkin);
	int delete(Long id);
	List<Checkin> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	Checkin find(Long id);
	List<Map> getStatsByMonth();
	List<Map> getStatsByDay();
}
