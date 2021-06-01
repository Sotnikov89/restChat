package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('room:read')")
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('room:read')")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        Optional<Room> room = roomService.findById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('room:read')")
    public List<Room> getByUserId(@PathVariable Long id) {
        return roomService.findByUserId(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('room:write')")
    public Room create(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('message:read')")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        roomService.save(room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{roomId}/user/{userId}")
    @PreAuthorize("hasAuthority('message:read')")
    public ResponseEntity<Void> addOrDeleteUserFromRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        return new ResponseEntity<>(roomService.addOrDeleteUserFromRoom(roomId, userId) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('message:read')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
