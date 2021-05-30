package ru.balabolka.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balabolka.chat.domain.Message;
import ru.balabolka.chat.domain.Room;
import ru.balabolka.chat.domain.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByRoom(Room room);
    List<Message> findMessagesByAuthor(User user);
}
