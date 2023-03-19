package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingTaskStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TextMatchingTaskRestDto(UUID id, String status, String inputText, String pattern, BigDecimal taskProgress) {
}
