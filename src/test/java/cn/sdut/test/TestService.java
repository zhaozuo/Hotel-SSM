package cn.sdut.test;

import cn.sdut.dao.StaffDao;
import cn.sdut.domain.Staff;
import cn.sdut.service.StaffService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestService {
    @Test
    public void testSpring() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        StaffService staffService = ac.getBean("staffService", StaffService.class);
        staffService.findAll();
    }

    @Test
    public void testMybatis() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StaffDao staffDao = sqlSession.getMapper(StaffDao.class);
        List<Staff> staffList = staffDao.findAllStaff();
        for (Staff staff: staffList){
            System.out.println(staff);
        }
        sqlSession.close();
        inputStream.close();
    }
}
