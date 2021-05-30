package ru.balabolka.chat.services;

import ru.balabolka.chat.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> findAll();
    List<Room> findByUserId(Long id);
    Optional<Room> findById(Long id);
    Room save(Room room);
    boolean delete(Long id);
    boolean addOrDeleteUserFromRoom(Long roomId, Long userId);
}
