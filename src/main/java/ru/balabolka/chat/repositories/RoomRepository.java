package ru.balabolka.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balabolka.chat.domain.Room;
import ru.balabolka.chat.domain.User;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomByMembersContaining(User user);
}
