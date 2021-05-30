package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.balabolka.chat.domain.Room;
import ru.balabolka.chat.services.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        Optional<Room> room = roomService.findById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/user/{id}")
    public List<Room> getByUserId(@PathVariable Long id) {
        return roomService.findByUserId(id);
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Room room) {
        roomService.save(room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{roomId}/user/{userId}")
    public ResponseEntity<Void> addOrDeleteUserFromRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        return new ResponseEntity<>(roomService.addOrDeleteUserFromRoom(roomId, userId) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
