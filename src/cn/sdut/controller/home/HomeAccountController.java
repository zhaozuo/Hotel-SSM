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
 * 前台用户控制器
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
     * 前台用户中心首页
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
     * 顾客在首页点击预订房间
     *
     * @param model 模型
     * @return 模型
     */
    @RequestMapping(value = "/book_order", method = RequestMethod.GET)
    public ModelAndView bookOrder(ModelAndView model, Long roomTypeId) {
        model.addObject("roomType", roomTypeService.find(roomTypeId));
        model.setViewName("home/account/book_order");
        return model;
    }

    /**
     * 预订信息提交
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
            ret.put("msg", "请填写正确的预订订单信息!");
            return ret;
        }
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            ret.put("type", "error");
            ret.put("msg", "顾客不能为空!");
            return ret;
        }
        bookOrder.setAccountId(account.getId());
        if (bookOrder.getRoomTypeId() == null) {
            ret.put("type", "error");
            ret.put("msg", "房型不能为空!");
            return ret;
        }
        if (bookOrder.getName().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "预订订单联系人名称不能为空!");
            return ret;
        }
        if (bookOrder.getMobile().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "预订订单联系人手机号不能为空!");
            return ret;
        }
        if (bookOrder.getIdCard().isBlank()) {
            ret.put("type", "error");
            ret.put("msg", "联系人身份证号不能为空!");
            return ret;
        }
        try{
            LocalDate arriveDate = LocalDate.parse(bookOrder.getArriveDate());
            LocalDate leaveDate = LocalDate.parse(bookOrder.getLeaveDate());
            if (arriveDate.isBefore(LocalDate.now()) || leaveDate.isBefore(LocalDate.now())){
                ret.put("type", "error");
                ret.put("msg", "不能选择今天之前的日期!");
                return ret;
            }
            if (leaveDate.isBefore(arriveDate)){
                ret.put("type", "error");
                ret.put("msg", "离店时间不能早于到达时间!");
                return ret;
            }
        }catch (DateTimeParseException e){
            ret.put("type", "error");
            ret.put("msg", "时间格式有误，请检查格式！");
            return ret;
        }
        bookOrder.setCreateTime(new Date());
        bookOrder.setStatus(0);
        if (bookOrderService.add(bookOrder) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        RoomType roomType = roomTypeService.find(bookOrder.getRoomTypeId());
        //预订成功后去修改该房型的预订数
        if (roomType != null) {
            if(roomType.getAvailableNum() == 0){
                ret.put("type", "error");
                ret.put("msg", "该房型已住满!");
                return ret;
            }
            roomType.setBookNum(roomType.getBookNum() + 1);
            roomType.setAvailableNum(roomType.getAvailableNum() - 1);
            roomTypeService.updateNum(roomType);
            //如果可用的房间数为0，则设置该房型状态已满
            if (roomType.getAvailableNum() == 0) {
                roomType.setStatus(0);
                roomTypeService.edit(roomType);
            }
        }
        ret.put("type", "success");
        ret.put("msg", "预订成功!");
        return ret;
    }

    /**
     * 修改个人信息提交
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
            retMap.put("msg", "请填写正确的用户信息！");
            return retMap;
        }
        if (StringUtils.isEmpty(account.getName())) {
            retMap.put("type", "error");
            retMap.put("msg", "用户名不能为空！");
            return retMap;
        }
        Account loginedAccount = (Account) request.getSession().getAttribute("account");
        if (isExist(account.getName(), loginedAccount.getId())) {
            retMap.put("type", "error");
            retMap.put("msg", "该用户名已经存在！");
            return retMap;
        }
        loginedAccount.setAddress(account.getAddress());
        loginedAccount.setIdCard(account.getIdCard());
        loginedAccount.setMobile(account.getMobile());
        loginedAccount.setName(account.getName());
        loginedAccount.setRealName(account.getRealName());
        if (accountService.edit(loginedAccount) <= 0) {
            retMap.put("type", "error");
            retMap.put("msg", "修改失败，请联系管理员！");
            return retMap;
        }
        request.getSession().setAttribute("account", loginedAccount);
        retMap.put("type", "success");
        retMap.put("msg", "修改成功！");
        return retMap;
    }

    /**
     * 修改密码提交
     */
    @RequestMapping(value = "/update_pwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updatePwdAct(String oldPassword, String newPassword, HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(oldPassword)) {
            retMap.put("type", "error");
            retMap.put("msg", "请填写原来的密码！");
            return retMap;
        }
        if (StringUtils.isEmpty(newPassword)) {
            retMap.put("type", "error");
            retMap.put("msg", "请填写新密码！");
            return retMap;
        }
        Account loginedAccount = (Account) request.getSession().getAttribute("account");
        if (!oldPassword.equals(loginedAccount.getPassword())) {
            retMap.put("type", "error");
            retMap.put("msg", "原密码错误！");
            return retMap;
        }
        loginedAccount.setPassword(newPassword);
        if (accountService.edit(loginedAccount) <= 0) {
            retMap.put("type", "error");
            retMap.put("msg", "修改失败，请联系管理员！");
            return retMap;
        }
        retMap.put("type", "success");
        retMap.put("msg", "修改密码成功！");
        return retMap;
    }

    /**
     * 顾客上传头像
     *
     * @param photo   照片文件
     * @param request 路径请求
     * @return 上传结果
     */
    @RequestMapping(value = "/upload_photo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        Map<String, String> ret = new HashMap<>();
        if (photo == null) {
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if (photo.getSize() > 1024 * 1024 * 1024) {
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }
        String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在改目录，则创建目录
            savePathFile.mkdir();
        }
        String filename = new Date().getTime() + "." + suffix;
        try {
            //将文件保存至指定目录
            photo.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "用户删除成功！");
        ret.put("filepath", request.getServletContext().getContextPath() + "/resources/upload/" + filename);
        return ret;
    }

    /**
     * 将图片存储到数据库
     *
     * @param photo     照片文件路径
     * @param accountId 用户id
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
     * 判断用户是否存在
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
