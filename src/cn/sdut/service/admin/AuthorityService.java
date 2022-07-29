package cn.sdut.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Authority;

/**
 * 权限service接口
 *
 */
@Service
public interface AuthorityService {
	int add(Authority authority);
	int deleteByRoleId(Long roleId);
	List<Authority> findListByRoleId(Long roleId);
}
