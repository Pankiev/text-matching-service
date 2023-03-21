package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingService;
import com.cdq.textmatching.domain.TextMatchingTask;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(("/text-matching"))
@AllArgsConstructor
@Log4j2
class TextMatchingController {

    private final TextMatchingService textMatchingService;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    List<TextMatchingTaskResultRestDto> getMatchingTasksInfo() {
        return textMatchingService.findTextMatchingTasks().stream()
                .map(TextMatchingTaskResultRestDto::of)
                .toList();
    }

    @GetMapping(value = {"/{id}", "/{id}/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TextMatchingTaskResultRestDto> getMatchingTaskInfo(@PathVariable("id") UUID id) {
        return textMatchingService.findTextMatchingTaskResult(id)
                .map(TextMatchingTaskResultRestDto::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    TextMatchingTaskRestDto startMatchingTask(@RequestBody StartTextMatchingTaskRestDto startTextMatchingTaskRestDto) {
        TextMatchingTask textMatchingTask = textMatchingService.startTextMatchingTask(
                startTextMatchingTaskRestDto.inputText(), startTextMatchingTaskRestDto.pattern());
        return TextMatchingTaskRestDto.of(textMatchingTask);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Invalid input parameters provided: {}", e.getMessage());
        return ResponseEntity.unprocessableEntity().body(new ErrorResponse(e.getMessage()));
    }
}
