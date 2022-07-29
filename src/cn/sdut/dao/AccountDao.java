package cn.sdut.dao;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import cn.sdut.entity.Account;

@Repository
public interface AccountDao {
	int add(Account account);
	int edit(Account account);
	int delete(Long id);
	int addPhoto(Account account);
	List<Account> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	Account find(Long id);
	Account findByName(String name);
	List<Account> findAll();
}
