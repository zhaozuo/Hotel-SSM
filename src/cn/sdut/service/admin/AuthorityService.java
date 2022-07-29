package cn.sdut.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.sdut.entity.admin.Authority;

/**
 * Ȩ��service�ӿ�
 *
 */
@Service
public interface AuthorityService {
	int add(Authority authority);
	int deleteByRoleId(Long roleId);
	List<Authority> findListByRoleId(Long roleId);
}
