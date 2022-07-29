package cn.sdut.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdut.service.AccountService;
import cn.sdut.util.FaceCompare;
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
import cn.sdut.entity.admin.Checkin;
import cn.sdut.entity.admin.Room;
import cn.sdut.page.admin.Page;
import cn.sdut.service.BookOrderService;
import cn.sdut.service.RoomTypeService;
import cn.sdut.service.admin.CheckinService;
import cn.sdut.service.admin.RoomService;

/**
 * ��ס�����̨������
 *
 */
@RequestMapping("/admin/checkin")
@Controller
public class CheckinController {
	
	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private BookOrderService bookOrderService;
	@Autowired
	private CheckinService checkinService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * ��ס�����б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.addObject("roomList", roomService.findAll());
		model.addObject("accountList", accountService.findAll());
		model.setViewName("checkin/list");
		return model;
	}


	@RequestMapping(value = "/comparePhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> comparePhoto(@RequestParam String photo1, @RequestParam String photo2){
		Map<String, String> ret = new HashMap<>();
		try {
			double result = FaceCompare.compare_image(photo1, photo2);
			ret.put("type", "success");
			ret.put("result", String.valueOf(result));
			if (result >= 0.4){
				ret.put("msg", "����ƥ��!");
			}else{
				ret.put("msg", "������ƥ��!");
			}
		} catch (Exception e){
			ret.put("type", "error");
			ret.put("msg", "��������");
			e.printStackTrace();
		}

		return ret;
	}
	
	/**
	 * ��ס��Ϣ��Ӳ���
	 * @param checkin
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Checkin checkin,
			@RequestParam(name="bookOrderId",required=false) Long bookOrderId
			){
		Map<String, String> ret = new HashMap<>();
		if(checkin == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����ס��Ϣ!");
			return ret;
		}
		if(checkin.getRoomId() == null){
			ret.put("type", "error");
			ret.put("msg", "���䲻��Ϊ��!");
			return ret;
		}
		if(checkin.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "���Ͳ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getName())){
			ret.put("type", "error");
			ret.put("msg", "��ס��ϵ�����Ʋ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "��ס��ϵ���ֻ��Ų���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "��ϵ�����֤�Ų���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getArriveDate())){
			ret.put("type", "error");
			ret.put("msg", "����ʱ�䲻��Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getLeaveDate())){
			ret.put("type", "error");
			ret.put("msg", "���ʱ�䲻��Ϊ��!");
			return ret;
		}
		checkin.setCreateTime(new Date());
		if(checkinService.add(checkin) <= 0){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		RoomType roomType = roomTypeService.find(checkin.getRoomTypeId());
		
		if(bookOrderId != null){
			//��Ԥ��������ס��(��ס�ȿ�����ֱ����סҲ�������Ѿ�Ԥ����������ס)
			BookOrder bookOrder = bookOrderService.find(bookOrderId);
			bookOrder.setStatus(1);
			bookOrderService.edit(bookOrder);
			//roomType.setBookNum(roomType.getBookNum() - 1);//Ԥ������1
		}else{
			roomType.setAvailableNum(roomType.getAvailableNum() - 1);
		}
		//��ס�ɹ���ȥ�޸ĸ÷��͵�Ԥ����
		if(roomType != null){
			roomType.setLivedNum(roomType.getLivedNum() + 1);//��ס����1
			roomTypeService.updateNum(roomType);
			//������õķ�����Ϊ0�������ø÷���״̬����
			if(roomType.getAvailableNum() == 0){
				roomType.setStatus(0);
				roomTypeService.edit(roomType);
			}
		}
		Room room = roomService.find(checkin.getRoomId());
		if(room != null){
			//Ҫ�ѷ���״̬����Ϊ����ס
			room.setStatus(1);
			roomService.edit(room);
		}
		ret.put("type", "success");
		ret.put("msg", "��ס�ɹ�!");
		return ret;
	}
	
	/**
	 * ��ס��Ϣ�༭����
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Checkin checkin,@RequestParam(name="bookOrderId",required=false) Long bookOrderId){
		Map<String, String> ret = new HashMap<String, String>();
		if(checkin == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����ס��Ϣ!");
			return ret;
		}
		if(checkin.getRoomId() == null){
			ret.put("type", "error");
			ret.put("msg", "���䲻��Ϊ��!");
			return ret;
		}
		if(checkin.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "���Ͳ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getName())){
			ret.put("type", "error");
			ret.put("msg", "��ס��ϵ�����Ʋ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "��ס��ϵ���ֻ��Ų���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "��ϵ�����֤�Ų���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getArriveDate())){
			ret.put("type", "error");
			ret.put("msg", "����ʱ�䲻��Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(checkin.getLeaveDate())){
			ret.put("type", "error");
			ret.put("msg", "���ʱ�䲻��Ϊ��!");
			return ret;
		}
		Checkin existCheckin = checkinService.find(checkin.getId());
		if(existCheckin == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����ȷ����ס��Ϣ���б༭!");
			return ret;
		}
		if(checkinService.edit(checkin) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		//�༭�ɹ�֮��1���жϷ����Ƿ����仯��2���жϷ����Ƿ����仯��3���ж��Ƿ��Ǵ�Ԥ������������Ϣ
		//�����ж��Ƿ��Ǵ�Ԥ��������ס��Ϣ
		RoomType oldRoomType = roomTypeService.find(existCheckin.getRoomTypeId());
		RoomType newRoomType = roomTypeService.find(checkin.getRoomTypeId());
		
		//������ס������Ԥ��������Ӱ��
		if(oldRoomType.getId().longValue() != newRoomType.getId().longValue()){
			//˵�����ͷ����˱仯��ԭ���ķ�����ס���ָ����µķ�����ס������
			oldRoomType.setLivedNum(oldRoomType.getLivedNum() - 1);
			newRoomType.setLivedNum(newRoomType.getLivedNum() + 1);
			if(bookOrderId == null){
				oldRoomType.setAvailableNum(oldRoomType.getAvailableNum() + 1);
				newRoomType.setAvailableNum(newRoomType.getAvailableNum() - 1);
			}
		}
		roomTypeService.updateNum(newRoomType);
		roomTypeService.updateNum(oldRoomType);
		//�жϷ����Ƿ����仯
		if(checkin.getRoomId().longValue() != existCheckin.getRoomId().longValue()){
			//��ʾ���䷢���˱仯
			Room oldRoom = roomService.find(existCheckin.getRoomId());
			Room newRoom = roomService.find(checkin.getRoomId());
			oldRoom.setStatus(0);//ԭ���ķ������ס
			newRoom.setStatus(1);//���ڵķ�������ס
			roomService.edit(newRoom);
			roomService.edit(oldRoom);
		}
		ret.put("type", "success");
		ret.put("msg", "�޸ĳɹ�!");
		return ret;
	}
	
	/**
	 * ��ҳ��ѯ��ס��Ϣ
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="roomId",defaultValue="") Long roomId,
			@RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId,
			@RequestParam(name="idCard",defaultValue="") String idCard,
			@RequestParam(name="mobile",defaultValue="") String mobile,
			@RequestParam(name="status",required=false) Integer status,
			Page page
			){
		Map<String,Object> ret = new HashMap<String, Object>();
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("status", status);
		queryMap.put("roomId", roomId);
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("idCard", idCard);
		queryMap.put("mobile", mobile);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", checkinService.findList(queryMap));
		ret.put("total", checkinService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * �˷�����
	 * @param checkId
	 * @return
	 */
	@RequestMapping(value="/checkout",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkout(Long checkId
			){
		Map<String, String> ret = new HashMap<String, String>();
		if(checkId == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ������!");
			return ret;
		}
		Checkin checkin = checkinService.find(checkId);
		if(checkin == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����ȷ������!");
			return ret;
		}
		checkin.setStatus(1);
		if(checkinService.edit(checkin) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�˷�ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		//���Ȳ�������״̬
		Room room = roomService.find(checkin.getRoomId());
		if(room != null){
			room.setStatus(2);
			roomService.edit(room);
		}
		//����޸ķ��Ϳ���������ס����״̬
		RoomType roomType = roomTypeService.find(checkin.getRoomTypeId());
		if(roomType != null){
			roomType.setAvailableNum(roomType.getAvailableNum() + 1);
			if(roomType.getAvailableNum() > roomType.getRoomNum()){
				roomType.setAvailableNum(roomType.getRoomNum());
			}
			roomType.setLivedNum(roomType.getLivedNum() - 1);
			if(roomType.getStatus() == 0){
				roomType.setStatus(1);
			}
			if(checkin.getBookOrderId() != null){
				roomType.setBookNum(roomType.getBookNum() - 1);
			}
			roomTypeService.updateNum(roomType);
			roomTypeService.edit(roomType);
		}
		//�ж��Ƿ�����Ԥ��
		if(checkin.getBookOrderId() != null){
			BookOrder bookOrder = bookOrderService.find(checkin.getBookOrderId());
			bookOrder.setStatus(2);
			bookOrderService.edit(bookOrder);
			
		}
		ret.put("type", "success");
		ret.put("msg", "�˷��ɹ�!");
		return ret;
	}
	
	/**
	 * ���ݷ������ͻ�ȡ����
	 * @param roomTypeId
	 * @return
	 */
	@RequestMapping(value="/load_room_list",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> load_room_list(Long roomTypeId){
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("status", 0);
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 999);
		List<Room> roomList = roomService.findList(queryMap);
		for(Room room:roomList){
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("value", room.getId());
			option.put("text", room.getSn());
			retList.add(option);
		}
		return retList;
	}
}
