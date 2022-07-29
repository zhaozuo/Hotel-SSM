package cn.sdut.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Role;

/**
 * ½ÇÉ«role dao
 *
 */
@Repository
public interface RoleDao {
	int add(Role role);
	int edit(Role role);
	int delete(Long id);
	List<Role> findList(Map<String, Object> queryMap);
	int getTotal(Map<String, Object> queryMap);
	Role find(Long id);
}
