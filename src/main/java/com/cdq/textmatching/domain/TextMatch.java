package com.cdq.textmatching.domain;

public record TextMatch(int positionInInputText, int typosCount) {

    boolean matchesBetterThan(TextMatch other) {
        return this.typosCount < other.typosCount || this.positionInInputText < other.positionInInputText;
    }
}
