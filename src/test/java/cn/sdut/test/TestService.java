package cn.sdut.test;

import cn.sdut.dao.OrderDao;
import cn.sdut.dao.RoomDao;
import cn.sdut.dao.StaffDao;
import cn.sdut.dao.UserDao;
import cn.sdut.domain.Order;
import cn.sdut.domain.Room;
import cn.sdut.domain.Staff;
import cn.sdut.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestService {

    @Test
    public void testStaff() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StaffDao staffDao = sqlSession.getMapper(StaffDao.class);
        Staff staff = new Staff();
        staff.setId(4);
        staff.setUsername("测试修改");
        staff.setPassword("123");
        staff.setRole(2);
        staff.setIdNumber("371325199803140000");
        staff.setPhone("15615634268");
        int a = staffDao.updateStaff(staff);
        System.out.println(a);
    }

    @Test
    public void testUser(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        /*User user = new User();
        user.setId(1);
        user.setUsername("用户");
        user.setPassword("6662");
        user.setEmail("wang@126.com");
        user.setIdNumber("371325000003140000");
        user.setSex("f");
        user.setPhone("15615622229");
        user.setPhotoData("photo_data2");*/
        User user = userDao.findUserByPhone("15615622229");
        System.out.println(user);
    }

    @Test
    public void testRoom(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
        Room room = new Room();
        room.setName("商务双人床");
        room.setArea("30-35");
        room.setDescription("有WIFi，宽带上网，有窗户，一次性水杯");
        room.setPrice(new BigDecimal("128"));
        room.setQuantity(5);
        room.setPictureData("pictureData123");
        room.setIsOnline(1);
        room.setPeopleQuantity(2);
        int i = roomDao.addRoom(room);
        System.out.println(i);
    }

    @Test
    public void testOrder(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Order order = new Order();
        order.setUserId(1);
        order.setRoomId(1);
        order.setCreateTime(LocalDateTime.now());
        order.setBeginDate(LocalDate.of(2020,5,16));
        order.setEndDate(LocalDate.of(2020,5,17));
        order.setRoomQuantity(1);
        order.setPrice(new BigDecimal("98.0"));
        int i = orderDao.addOrder(order);
        System.out.println(i);
    }
}
