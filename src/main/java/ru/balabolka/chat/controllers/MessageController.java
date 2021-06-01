package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.balabolka.chat.domain.Message;
import ru.balabolka.chat.services.MessageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('message:read')")
    public List<Message> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('message:read')")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        Optional<Message> message = messageService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/room/{id}")
    @PreAuthorize("hasAuthority('message:read')")
    public List<Message> getByRoomId(@PathVariable Long id) {
        return messageService.findByRoomId(id);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('message:read')")
    public List<Message> getByUserId(@PathVariable Long id) {
        return messageService.findByUserId(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('message:write')")
    public Message create(@RequestBody Message message) {
        return messageService.save(message);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('message:write')")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        messageService.update(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('message:write')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
