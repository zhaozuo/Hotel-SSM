package cn.sdut.dao;

import cn.sdut.domain.Room;

import java.util.List;

public interface RoomDao {
    Room findRoomById(Integer id);
    List<Room> findAllRoom();
    int addRoom(Room room);
    int updateRoom(Room room);
    int deleteRoom(Integer id);
}
