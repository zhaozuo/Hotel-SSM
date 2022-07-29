package cn.sdut.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sdut.entity.Account;
import cn.sdut.page.admin.Page;
import cn.sdut.service.AccountService;

/**
 * 顾客管理后台控制器
 *
 */
@RequestMapping("/admin/account")
@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	/**
	 * 顾客管理列表页面
	 * @param model 模型
	 * @return 顾客管理页面
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("account/list");
		return model;
	}
	
	/**
	 * 顾客信息添加操作
	 * @param account 顾客实体
	 * @return 添加结果
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Account account){
		Map<String, String> ret = new HashMap<>();
		if(account == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的顾客信息!");
			return ret;
		}
		if(StringUtils.isEmpty(account.getName())){
			ret.put("type", "error");
			ret.put("msg", "顾客名称不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(account.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "顾客密码不能为空!");
			return ret;
		}
		if(isExist(account.getName(), 0L)){
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在!");
			return ret;
		}
		if(accountService.add(account) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}
	
	/**
	 * 顾客信息编辑操作
	 * @param account 顾客实体
	 * @return 编辑结果
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Account account){
		Map<String, String> ret = new HashMap<>();
		if(account == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的顾客信息!");
			return ret;
		}
		if(StringUtils.isEmpty(account.getName())){
			ret.put("type", "error");
			ret.put("msg", "顾客名称不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(account.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "顾客密码不能为空!");
			return ret;
		}
		if(isExist(account.getName(), account.getId())){
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在!");
			return ret;
		}
		if(accountService.edit(account) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功!");
		return ret;
	}
	
	/**
	 * 分页查询顾客信息
	 * @param name 顾客姓名
	 * @param page 分页实体
	 * @return 查询结果
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="realName",defaultValue="") String realName,
			@RequestParam(name="idCard",defaultValue="") String idCard,
			@RequestParam(name="mobile",defaultValue="") String mobile,
			@RequestParam(name="status",required=false) Integer status,
			Page page
			){
		Map<String,Object> ret = new HashMap<>();
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("name", name);
		queryMap.put("status", status);
		queryMap.put("realName", realName);
		queryMap.put("idCard", idCard);
		queryMap.put("mobile", mobile);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", accountService.findList(queryMap));
		ret.put("total", accountService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 顾客信息删除操作
	 * @param id 顾客id
	 * @return 删除结果
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的信息!");
			return ret;
		}
		try {
			if(accountService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员!");
				return ret;
			}
		} catch (Exception e) {
			ret.put("type", "error");
			ret.put("msg", "该顾客下存在订单信息，请先删除该顾客下的所有订单信息!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		return ret;
	}
	
	/**
	 * 判断用户名是否存在
	 * @param name 顾客姓名
	 * @param id 顾客id
	 * @return 查询结果
	 */
	private boolean isExist(String name,Long id){
		Account findByName = accountService.findByName(name);
		if(findByName == null)return false;
		if(findByName.getId().longValue() == id.longValue())return false;
		return true;
	}
}
