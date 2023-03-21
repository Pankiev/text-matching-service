package com.cdq.textmatching.domain;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TextMatchingTaskProcessorTest {

    private final TextMatchingTaskProcessor textMatchingTaskProcessor = new TextMatchingTaskProcessor(mock(ArtificialLoadSimulator.class));

    @Test
    void givenABCDInputTextAndBCDPattern_process_shouldEmit3PartlyProcessedTasksAndOneCompletedTaskWithPosition1And0Typos() {
        // given
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess("ABCD", "BCD");
        Consumer<TextMatchingTask> progressedTextMatchingTaskConsumer = mock(Consumer.class);
        // when
        textMatchingTaskProcessor.process(textMatchingTask, progressedTextMatchingTaskConsumer);
        // then
        verify(progressedTextMatchingTaskConsumer, times(4)).accept(any());
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(0, new TextMatch(0, 3)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(1, new TextMatch(1, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(2, new TextMatch(1, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newCompletedTask(new TextMatch(1, 0)));
    }

    @Test
    void givenABCDInputTextAndBWDPattern_process_shouldEmit3PartlyProcessedTasksAndOneCompletedTaskWithPosition1And1Typo() {
        // given
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess("ABCD", "BWD");
        Consumer<TextMatchingTask> progressedTextMatchingTaskConsumer = mock(Consumer.class);
        // when
        textMatchingTaskProcessor.process(textMatchingTask, progressedTextMatchingTaskConsumer);
        // then
        verify(progressedTextMatchingTaskConsumer, times(4)).accept(any());
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(0, new TextMatch(0, 3)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(1, new TextMatch(1, 1)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(2, new TextMatch(1, 1)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newCompletedTask(new TextMatch(1, 1)));
    }

    @Test
    void givenABCDEFGInputTextAndCFGPattern_process_shouldEmit6PartlyProcessedTasksAndOneCompletedTaskWithPosition4And1Typo() {
        // given
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess("ABCDEFG", "CFG");
        Consumer<TextMatchingTask> progressedTextMatchingTaskConsumer = mock(Consumer.class);
        // when
        textMatchingTaskProcessor.process(textMatchingTask, progressedTextMatchingTaskConsumer);
        // then
        verify(progressedTextMatchingTaskConsumer, times(7)).accept(any());
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(0, new TextMatch(0, 3)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(1, new TextMatch(0, 3)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(2, new TextMatch(2, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(3, new TextMatch(2, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(4, new TextMatch(4, 1)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(5, new TextMatch(4, 1)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newCompletedTask(new TextMatch(4, 1)));
    }

    @Test
    void givenABCABCInputTextAndABCPattern_process_shouldEmit5PartlyProcessedTasksAndOneCompletedTaskWithPosition0And0Typos() {
        // given
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess("ABCABC", "ABC");
        Consumer<TextMatchingTask> progressedTextMatchingTaskConsumer = mock(Consumer.class);
        // when
        textMatchingTaskProcessor.process(textMatchingTask, progressedTextMatchingTaskConsumer);
        // then
        verify(progressedTextMatchingTaskConsumer, times(6)).accept(any());
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(0, new TextMatch(0, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(1, new TextMatch(0, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(2, new TextMatch(0, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(3, new TextMatch(0, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(4, new TextMatch(0, 0)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newCompletedTask(new TextMatch(0, 0)));
    }

    @Test
    void givenABCDEFGInputTextAndTDDPattern_process_shouldEmit6PartlyProcessedTasksAndOneCompletedTaskWithPosition1And2Typos() {
        // given
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess("ABCDEFG", "TDD");
        Consumer<TextMatchingTask> progressedTextMatchingTaskConsumer = mock(Consumer.class);
        // when
        textMatchingTaskProcessor.process(textMatchingTask, progressedTextMatchingTaskConsumer);
        // then
        verify(progressedTextMatchingTaskConsumer, times(7)).accept(any());
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(0, new TextMatch(0, 3)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(1, new TextMatch(1, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(2, new TextMatch(1, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(3, new TextMatch(1, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(4, new TextMatch(1, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newPartlyProcessedTask(5, new TextMatch(1, 2)));
        verify(progressedTextMatchingTaskConsumer)
                .accept(textMatchingTask.newCompletedTask(new TextMatch(1, 2)));
    }

}
