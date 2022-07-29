package cn.sdut.controller.admin;

import java.util.Date;
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

import cn.sdut.entity.BookOrder;
import cn.sdut.entity.RoomType;
import cn.sdut.page.admin.Page;
import cn.sdut.service.AccountService;
import cn.sdut.service.BookOrderService;
import cn.sdut.service.RoomTypeService;

/**
 * 预订订单管理后台控制器
 *
 */
@RequestMapping("/admin/book_order")
@Controller
public class BookOrderController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private BookOrderService bookOrderService;
	
	
	/**
	 * 预订订单管理列表页面
	 * @param model 模型
	 * @return 结果
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.addObject("accountList", accountService.findAll());
		model.setViewName("book_order/list");
		return model;
	}
	
	/**
	 * 预订订单信息添加操作
	 * @param bookOrder
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(BookOrder bookOrder){
		Map<String, String> ret = new HashMap<>();
		if(bookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预订订单信息!");
			return ret;
		}
		if(bookOrder.getAccountId() == null){
			ret.put("type", "error");
			ret.put("msg", "顾客不能为空!");
			return ret;
		}
		if(bookOrder.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "房型不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getName())){
			ret.put("type", "error");
			ret.put("msg", "预订订单联系人名称不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "预订订单联系人手机号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "联系人身份证号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getArriveDate())){
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getLeaveDate())){
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空!");
			return ret;
		}
		bookOrder.setCreateTime(new Date());
		if(bookOrderService.add(bookOrder) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		RoomType roomType = roomTypeService.find(bookOrder.getRoomTypeId());
		//预订成功后去修改该房型的预订数
		if(roomType != null){
			roomType.setBookNum(roomType.getBookNum() + 1);
			roomType.setAvailableNum(roomType.getAvailableNum() - 1);
			roomTypeService.updateNum(roomType);
			//如果可用的房间数为0，则设置该房型状态已满
			if(roomType.getAvailableNum() == 0){
				roomType.setStatus(0);
				roomTypeService.edit(roomType);
			}
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}
	
	/**
	 * 预订订单信息编辑操作
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(BookOrder bookOrder){
		Map<String, String> ret = new HashMap<>();
		if(bookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预订订单信息!");
			return ret;
		}
		if(bookOrder.getAccountId() == null){
			ret.put("type", "error");
			ret.put("msg", "顾客不能为空!");
			return ret;
		}
		if(bookOrder.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "房型不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getName())){
			ret.put("type", "error");
			ret.put("msg", "预订订单联系人名称不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "预订订单联系人手机号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "联系人身份证号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getArriveDate())){
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getLeaveDate())){
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空!");
			return ret;
		}
		BookOrder existBookOrder = bookOrderService.find(bookOrder.getId());
		if(existBookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请选择正确的数据进行编辑!");
			return ret;
		}
		if(bookOrderService.edit(bookOrder) <= 0){
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员!");
			return ret;
		}
		//判断房型是否发生变化
		if(existBookOrder.getRoomTypeId().longValue() != bookOrder.getRoomTypeId().longValue()){
			//房型发生了变化
			//首先恢复原来房型的预订数及可用数
			RoomType oldRoomType = roomTypeService.find(existBookOrder.getRoomTypeId());
			oldRoomType.setAvailableNum(oldRoomType.getAvailableNum() + 1);
			oldRoomType.setBookNum(oldRoomType.getBookNum() - 1);
			roomTypeService.updateNum(oldRoomType);
			if(oldRoomType.getStatus() == 0){
				//旧的房间原来是满房，现在不满房的话，恢复状态
				if(oldRoomType.getAvailableNum() > 0){
					//设置成状态可用
					oldRoomType.setStatus(1);
					roomTypeService.edit(oldRoomType);
				}
			}
			//修改新的房型的可用数和预订数
			RoomType newRoomType = roomTypeService.find(bookOrder.getRoomTypeId());
			newRoomType.setAvailableNum(newRoomType.getAvailableNum() - 1);
			newRoomType.setBookNum(newRoomType.getBookNum() + 1);
			roomTypeService.updateNum(newRoomType);
			if(newRoomType.getAvailableNum() <= 0){
				//没有可用房间数
				newRoomType.setStatus(0);//设置成满房
				roomTypeService.edit(newRoomType);
			}
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功!");
		return ret;
	}
	
	/**
	 * 分页查询预订订单信息
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="accountId",defaultValue="") Long accountId,
			@RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId,
			@RequestParam(name="idCard",defaultValue="") String idCard,
			@RequestParam(name="mobile",defaultValue="") String mobile,
			@RequestParam(name="status",required=false) Integer status,
			Page page
			){
		Map<String,Object> ret = new HashMap<>();
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("name", name);
		queryMap.put("status", status);
		queryMap.put("accountId", accountId);
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("idCard", idCard);
		queryMap.put("mobile", mobile);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", bookOrderService.findList(queryMap));
		ret.put("total", bookOrderService.getTotal(queryMap));
		return ret;
	}
}
