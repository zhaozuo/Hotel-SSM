package cn.sdut.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sdut.entity.admin.Authority;

/**
 * Ȩ��ʵ����dao
 */
@Repository
public interface AuthorityDao {
	int add(Authority authority);
	int deleteByRoleId(Long roleId);
	List<Authority> findListByRoleId(Long roleId);
}
