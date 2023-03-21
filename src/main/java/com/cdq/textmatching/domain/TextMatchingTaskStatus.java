package com.cdq.textmatching.domain;

import java.util.EnumSet;

public enum TextMatchingTaskStatus {
    TO_PROCESS, PROCESSING, FINISHED, ERROR;

    private static final EnumSet<TextMatchingTaskStatus> REQUIRES_PROCESSING_STATUS_SET = EnumSet.of(TO_PROCESS, PROCESSING, ERROR);

    public boolean doesIndicateNeedForProcessing() {
        return REQUIRES_PROCESSING_STATUS_SET.contains(this);
    }
}
