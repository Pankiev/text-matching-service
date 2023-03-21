package com.cdq.textmatching.domain;

import java.math.BigDecimal;

public record TextMatchingTaskResult(TextMatchingTask textMatchingTask, BigDecimal progressPercentage) {
}
