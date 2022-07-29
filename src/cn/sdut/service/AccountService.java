package cn.sdut.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.sdut.entity.Account;

@Service
public interface AccountService {
	int add(Account account);
	int edit(Account account);
	int addPhoto(Account account);
	int delete(Long id);
	List<Account> findList(Map<String, Object> queryMap);
	Integer getTotal(Map<String, Object> queryMap);
	Account find(Long id);
	Account findByName(String name);
	List<Account> findAll();
}
