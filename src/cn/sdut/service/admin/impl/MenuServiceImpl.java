package cn.sdut.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sdut.dao.admin.MenuDao;
import cn.sdut.entity.admin.Menu;
import cn.sdut.service.admin.MenuService;
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> findList(Map<String, Object> queryMap) {
		return menuDao.findList(queryMap);
	}

	@Override
	public List<Menu> findListByIds(String ids) {
		return menuDao.findListByIds(ids);
	}

}
