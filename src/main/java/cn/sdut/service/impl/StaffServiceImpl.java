package cn.sdut.service.impl;

import cn.sdut.dao.StaffDao;
import cn.sdut.domain.Staff;
import cn.sdut.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    private final StaffDao staffDao;
    @Autowired
    public StaffServiceImpl(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public List<Staff> findAll() {
        return staffDao.findAllStaff();
    }

    @Override
    @Transactional
    public void addStaff(Staff staff) {
        staffDao.addStaff(staff);
    }
}
