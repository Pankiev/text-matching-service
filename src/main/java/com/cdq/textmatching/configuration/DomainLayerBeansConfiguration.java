package com.cdq.textmatching.configuration;

import com.cdq.textmatching.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class serves as a component that allows us to clear domain layer from spring framework annotations,
// aiming to make domain layer completely independent of controller and infrastructure layers.
// Having achieved this, we can freely modify other layers without making modifications in domain layer.
// For e.g. we can change message broker system, change database or use different web server framework
@Configuration
public class DomainLayerBeansConfiguration {

    @Bean
    public TextMatchingService textMatchingService(TextMatchingTaskRepository textMatchingTaskRepository,
                                                   TextMatchingTaskMessagePublisher textMatchingTaskMessagePublisher) {
        return new TextMatchingService(textMatchingTaskRepository, textMatchingTaskMessagePublisher);
    }

    @Bean
    public TextMatchingTaskMessageHandler textMatchingTaskMessageHandler(
            TextMatchingTaskRepository textMatchingTaskRepository, TextMatchingTaskProcessor textMatchingTaskProcessor) {
        return new TextMatchingTaskMessageHandler(textMatchingTaskRepository, textMatchingTaskProcessor);
    }

    @Bean
    public ArtificialLoadSimulator artificialLoadSimulator() {
        return new ThreadSleepArtificialLoadSimulator();
    }

    @Bean
    public TextMatchingTaskProcessor textMatchingTaskProcessor(ArtificialLoadSimulator artificialLoadSimulator) {
        return new TextMatchingTaskProcessor(artificialLoadSimulator);
    }
}
