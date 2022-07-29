package cn.sdut.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Menu;

/**
 * �˵�����service
 *
 */
@Service
public interface MenuService {
	List<Menu> findList(Map<String, Object> queryMap);
	List<Menu> findListByIds(String ids);
}
