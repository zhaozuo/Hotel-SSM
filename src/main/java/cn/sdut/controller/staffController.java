package cn.sdut.controller;

import cn.sdut.domain.Staff;
import cn.sdut.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class staffController {
    private final StaffService staffService;
    @Autowired
    public staffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @RequestMapping("/testList")
    public String testList(Model model){
        System.out.println("testList执行了...");
        List<Staff> staffList = staffService.findAll();
        model.addAttribute("staffList", staffList);
        return "list";
    }

    @RequestMapping("/testAdd")
    public void testAdd(Staff staff, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("testAdd执行了...");
        staffService.addStaff(staff);
        response.sendRedirect(request.getContextPath() + "/staff/testList");
    }
}
