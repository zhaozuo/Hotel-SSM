package cn.sdut.dao.admin;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Checkin;

@Repository
public interface CheckinDao {
	int add(Checkin checkin);
	int edit(Checkin checkin);
	int delete(Long id);
	List<Checkin> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	Checkin find(Long id);
	List<Map> getStatsByMonth();
	List<Map> getStatsByDay();
}
