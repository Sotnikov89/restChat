package ru.balabolka.chat.services;

import ru.balabolka.chat.domain.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> findAll();
    List<Message> findByRoomId(Long id);
    List<Message> findByUserId(Long id);
    Optional<Message> findById(Long id);
    Message save(Message message);
    void update(Message message);
    boolean delete(Long id);

}
