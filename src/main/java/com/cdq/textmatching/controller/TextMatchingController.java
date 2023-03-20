package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingService;
import com.cdq.textmatching.domain.TextMatchingTask;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(("/text-matching"))
@AllArgsConstructor
public class TextMatchingController {

    private final TextMatchingService textMatchingService;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TextMatchingTaskRestDto> getMatchingTasksInfo() {
        return textMatchingService.findTextMatchingTasks().stream()
                .map(this::toTextMatchingTaskRestDto)
                .toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextMatchingTaskRestDto> getMatchingTaskInfo(@PathVariable("id") UUID id) {
        return textMatchingService.findTextMatchingTask(id)
                .map(this::toTextMatchingTaskRestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private TextMatchingTaskRestDto toTextMatchingTaskRestDto(TextMatchingTask textMatchingTask) {
        return new TextMatchingTaskRestDto(textMatchingTask.id(), textMatchingTask.status().name(),
                textMatchingTask.inputText(), textMatchingTask.pattern(), textMatchingTask.taskProgress());
    }

    @PostMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TextMatchingTaskRestDto startMatchingTask(@RequestBody StartTextMatchingTaskRestDto startTextMatchingTaskRestDto) {
        TextMatchingTask textMatchingTask = textMatchingService.startTextMatchingTask(
                startTextMatchingTaskRestDto.inputText(), startTextMatchingTaskRestDto.pattern());
        return toTextMatchingTaskRestDto(textMatchingTask);
    }
}
