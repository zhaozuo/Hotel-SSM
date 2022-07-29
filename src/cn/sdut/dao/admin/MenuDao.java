package cn.sdut.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Menu;

/**
 * ≤Àµ•π‹¿Ìdao
 *
 */
@Repository
public interface MenuDao {
	List<Menu> findList(Map<String, Object> queryMap);
	List<Menu> findListByIds(String ids);
}
