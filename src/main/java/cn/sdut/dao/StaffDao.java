package cn.sdut.dao;

import cn.sdut.domain.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffDao {
    List<Staff> findAllStaff();
    Staff findStaffById(Integer id);

    /**
     * 用于使用搜索框模糊查找员工
     * @param name 员工姓名
     * @return 员工列表
     */
    List<Staff> findStaffByName(String name);
    int addStaff(Staff staff);
    int deleteStaff(Integer id);
    int updateStaff(Staff staff);
}
