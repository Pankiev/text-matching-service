package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingService;
import com.cdq.textmatching.domain.TextMatchingTask;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(("/text-matching"))
@AllArgsConstructor
public class TextMatchingController {

    private final TextMatchingService textMatchingService;

    @GetMapping("/{id}")
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

    @PostMapping("/start-new-task")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TextMatchingTaskRestDto startMatchingTask(@RequestBody StartTextMatchingTaskRestDto startTextMatchingTaskRestDto) {
        TextMatchingTask textMatchingTask = textMatchingService.startMatchingTask(
                startTextMatchingTaskRestDto.inputText(), startTextMatchingTaskRestDto.pattern());
        return toTextMatchingTaskRestDto(textMatchingTask);
    }
}
