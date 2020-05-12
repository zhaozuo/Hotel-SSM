package cn.sdut.service;

import cn.sdut.domain.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> findAll();
    void addStaff(Staff staff);
}
