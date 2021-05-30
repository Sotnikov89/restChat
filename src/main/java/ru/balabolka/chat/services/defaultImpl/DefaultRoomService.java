package ru.balabolka.chat.services.defaultImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balabolka.chat.domain.Room;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.repositories.RoomRepository;
import ru.balabolka.chat.repositories.UserRepository;
import ru.balabolka.chat.services.RoomService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultRoomService implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> findByUserId(Long id) {
        return roomRepository.findRoomByMembersContaining(User.builder().id(id).build());
    }

    @Override
    public boolean addOrDeleteUserFromRoom(Long roomId, Long userId) {
        boolean rsl = true;
        try {
            Room room = roomRepository.getById(roomId);
            User user = userRepository.getById(userId);
            if (room.getMembers().contains(user)) {
                room.getMembers().remove(user);
            } else {
                room.addMember(user);
            }
            roomRepository.save(room);
        } catch (Exception e) {
            rsl = false;
        }
        return rsl;
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public boolean delete(Long id) {
        boolean rsl = false;
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            rsl = true;
        }
        return rsl;
    }
}
