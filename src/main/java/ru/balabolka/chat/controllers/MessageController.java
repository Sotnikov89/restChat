package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Message> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        Optional<Message> message = messageService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/room/{id}")
    public List<Message> getByRoomId(@PathVariable Long id) {
        return messageService.findByRoomId(id);
    }

    @GetMapping("/user/{id}")
    public List<Message> getByUserId(@PathVariable Long id) {
        return messageService.findByUserId(id);
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageService.save(message);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Message message) {
        messageService.update(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
