package ru.balabolka.chat.services.defaultImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balabolka.chat.domain.Message;
import ru.balabolka.chat.domain.Room;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.repositories.MessageRepository;
import ru.balabolka.chat.repositories.RoomRepository;
import ru.balabolka.chat.services.MessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findByRoomId(Long id) {
        return messageRepository.findMessagesByRoom(Room.builder().id(id).build());
    }

    @Override
    public List<Message> findByUserId(Long id) {
        return messageRepository.findMessagesByAuthor(User.builder().id(id).build());
    }

    @Override
    public Message save(Message message) {
        Room roomFromDb = roomRepository.getById(message.getRoom().getId());
        roomFromDb.addMember(message.getAuthor());
        roomRepository.save(roomFromDb);

        message.setCreated(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public void update(Message message) {
        Optional<Message> messageFromDb = messageRepository.findById(message.getId());
        if (messageFromDb.isPresent()) {
            message.setCreated(messageFromDb.get().getCreated());
        } else {
            message.setCreated(LocalDateTime.now());
        }
        messageRepository.save(message);
    }

    @Override
    public boolean delete(Long id) {
        boolean rsl = false;
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            rsl = true;
        }
        return rsl;
    }
}
