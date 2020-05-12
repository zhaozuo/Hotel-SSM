package cn.sdut.dao;

import cn.sdut.domain.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffDao {
    List<Staff> findAllStaff();
    void addStaff(Staff staff);
}
