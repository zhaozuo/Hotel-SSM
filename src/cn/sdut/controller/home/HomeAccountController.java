package cn.sdut.controller.home;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.sdut.entity.Account;
import cn.sdut.entity.BookOrder;
import cn.sdut.entity.RoomType;
import cn.sdut.service.AccountService;
import cn.sdut.service.BookOrderService;
import cn.sdut.service.RoomTypeService;

/**
 * ǰ̨�û�������
 */
@RequestMapping("/home/account")
@Controller
public class HomeAccountController {

    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BookOrderService bookOrderService;

    /**
     * ǰ̨�û�������ҳ
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("accountId", account.getId());
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 999);
        model.addObject("bookOrderList", bookOrderService.findList(queryMap));
        model.addObject("roomTypeList", roomTypeService.findAll());
        model.setViewName("home/account/index");
        return model;
    }

    /**
     * �˿�����ҳ���Ԥ������
     *
     * @param model ģ��
     * @return ģ��
     */
    @RequestMapping(value = "/book_order", method = RequestMethod.GET)
    public ModelAndView bookOrder(ModelAndView model, Long roomTypeId) {
        model.addObject("roomType", roomTypeService.find(roomTypeId));
        model.setViewName("home/account/book_order");
        return model;
    }

    /**
     * Ԥ����Ϣ�ύ
     *
     * @param bookOrder
     * @return
     */
    @RequestMapping(value = "/book_order", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> bookOrderAct(BookOrder bookOrder, HttpServletRequest request) {
        Map<String, String> ret = new HashMap<>();
        if (bookOrder == null) {
            ret.put("type", "error");
            ret.put("msg", "����д��ȷ��Ԥ��������Ϣ!");
            return ret;
        }
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            ret.put("type", "error");
            ret.put("msg", "�˿Ͳ���Ϊ��!");
            return ret;
        }
        bookOrder.setAccountId(account.getId());
        if (bookOrder.getRoomTypeId() == null) {
            ret.put("type", "error");
            ret.put("msg", "���Ͳ���Ϊ��!");
            return ret;
        }
        if (bookOrder.getName().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "Ԥ��������ϵ�����Ʋ���Ϊ��!");
            return ret;
        }
        if (bookOrder.getMobile().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "Ԥ��������ϵ���ֻ��Ų���Ϊ��!");
            return ret;
        }
        if (bookOrder.getIdCard().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "��ϵ�����֤�Ų���Ϊ��!");
            return ret;
        }
        try{
            LocalDate arriveDate = LocalDate.parse(bookOrder.getArriveDate());
            LocalDate leaveDate = LocalDate.parse(bookOrder.getLeaveDate());
            if (arriveDate.isBefore(LocalDate.now()) || leaveDate.isBefore(LocalDate.now())){
                ret.put("type", "error");
                ret.put("msg", "����ѡ�����֮ǰ������!");
                return ret;
            }
            if (leaveDate.isBefore(arriveDate)){
                ret.put("type", "error");
                ret.put("msg", "���ʱ�䲻�����ڵ���ʱ��!");
                return ret;
            }
        }catch (DateTimeParseException e){
            ret.put("type", "error");
            ret.put("msg", "ʱ���ʽ���������ʽ��");
            return ret;
        }
        bookOrder.setCreateTime(new Date());
        bookOrder.setStatus(0);
        if (bookOrderService.add(bookOrder) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
            return ret;
        }
        RoomType roomType = roomTypeService.find(bookOrder.getRoomTypeId());
        //Ԥ���ɹ���ȥ�޸ĸ÷��͵�Ԥ����
        if (roomType != null) {
            if(roomType.getAvailableNum() == 0){
                ret.put("type", "error");
                ret.put("msg", "�÷�����ס��!");
                return ret;
            }
            roomType.setBookNum(roomType.getBookNum() + 1);
            roomType.setAvailableNum(roomType.getAvailableNum() - 1);
            roomTypeService.updateNum(roomType);
            //������õķ�����Ϊ0�������ø÷���״̬����
            if (roomType.getAvailableNum() == 0) {
                roomType.setStatus(0);
                roomTypeService.edit(roomType);
            }
        }
        ret.put("type", "success");
        ret.put("msg", "Ԥ���ɹ�!");
        return ret;
    }

    /**
     * �޸ĸ�����Ϣ�ύ
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/update_info", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateInfoAct(Account account, HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();
        if (account == null) {
            retMap.put("type", "error");
            retMap.put("msg", "����д��ȷ���û���Ϣ��");
            return retMap;
        }
        if (StringUtils.isEmpty(account.getName())) {
            retMap.put("type", "error");
            retMap.put("msg", "�û�������Ϊ�գ�");
            return retMap;
        }
        Account loginedAccount = (Account) request.getSession().getAttribute("account");
        if (isExist(account.getName(), loginedAccount.getId())) {
            retMap.put("type", "error");
            retMap.put("msg", "���û����Ѿ����ڣ�");
            return retMap;
        }
        loginedAccount.setAddress(account.getAddress());
        loginedAccount.setIdCard(account.getIdCard());
        loginedAccount.setMobile(account.getMobile());
        loginedAccount.setName(account.getName());
        loginedAccount.setRealName(account.getRealName());
        if (accountService.edit(loginedAccount) <= 0) {
            retMap.put("type", "error");
            retMap.put("msg", "�޸�ʧ�ܣ�����ϵ����Ա��");
            return retMap;
        }
        request.getSession().setAttribute("account", loginedAccount);
        retMap.put("type", "success");
        retMap.put("msg", "�޸ĳɹ���");
        return retMap;
    }

    /**
     * �޸������ύ
     */
    @RequestMapping(value = "/update_pwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updatePwdAct(String oldPassword, String newPassword, HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(oldPassword)) {
            retMap.put("type", "error");
            retMap.put("msg", "����дԭ�������룡");
            return retMap;
        }
        if (StringUtils.isEmpty(newPassword)) {
            retMap.put("type", "error");
            retMap.put("msg", "����д�����룡");
            return retMap;
        }
        Account loginedAccount = (Account) request.getSession().getAttribute("account");
        if (!oldPassword.equals(loginedAccount.getPassword())) {
            retMap.put("type", "error");
            retMap.put("msg", "ԭ�������");
            return retMap;
        }
        loginedAccount.setPassword(newPassword);
        if (accountService.edit(loginedAccount) <= 0) {
            retMap.put("type", "error");
            retMap.put("msg", "�޸�ʧ�ܣ�����ϵ����Ա��");
            return retMap;
        }
        retMap.put("type", "success");
        retMap.put("msg", "�޸�����ɹ���");
        return retMap;
    }

    /**
     * �˿��ϴ�ͷ��
     *
     * @param photo   ��Ƭ�ļ�
     * @param request ·������
     * @return �ϴ����
     */
    @RequestMapping(value = "/upload_photo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        Map<String, String> ret = new HashMap<>();
        if (photo == null) {
            ret.put("type", "error");
            ret.put("msg", "ѡ��Ҫ�ϴ����ļ���");
            return ret;
        }
        if (photo.getSize() > 1024 * 1024 * 1024) {
            ret.put("type", "error");
            ret.put("msg", "�ļ���С���ܳ���10M��");
            return ret;
        }
        //��ȡ�ļ���׺
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "��ѡ��jpg,jpeg,gif,png��ʽ��ͼƬ��");
            return ret;
        }
        String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //�������ڸ�Ŀ¼���򴴽�Ŀ¼
            savePathFile.mkdir();
        }
        String filename = new Date().getTime() + "." + suffix;
        try {
            //���ļ�������ָ��Ŀ¼
            photo.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "�����ļ��쳣��");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "�û�ɾ���ɹ���");
        ret.put("filepath", request.getServletContext().getContextPath() + "/resources/upload/" + filename);
        return ret;
    }

    /**
     * ��ͼƬ�洢�����ݿ�
     *
     * @param photo     ��Ƭ�ļ�·��
     * @param accountId �û�id
     * @return
     */
    @RequestMapping(value = "savePhoto", method = RequestMethod.POST)
    public String savePhoto(@RequestParam String photo, @RequestParam Long accountId) {
        Account account = new Account();
        account.setId(accountId);
        account.setPhoto(photo);
        accountService.addPhoto(account);
        return "home/account/index";
    }

    /**
     * �ж��û��Ƿ����
     *
     * @param name
     * @param id
     * @return
     */
    private boolean isExist(String name, Long id) {
        Account account = accountService.findByName(name);
        if (account == null) return false;
        if (account != null && account.getId().longValue() == id) return false;
        return true;
    }
}
