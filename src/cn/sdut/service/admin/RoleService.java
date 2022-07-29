package cn.sdut.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Role;

/**
 * ½ÇÉ«role service
 *
 */
@Service
public interface RoleService {
	int add(Role role);
	int edit(Role role);
	int delete(Long id);
	List<Role> findList(Map<String, Object> queryMap);
	int getTotal(Map<String, Object> queryMap);
	Role find(Long id);
}
