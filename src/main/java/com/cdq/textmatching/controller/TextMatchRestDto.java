package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatch;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
record TextMatchRestDto(int position, int typos) {

    static TextMatchRestDto of(TextMatch bestMatch) {
        return new TextMatchRestDto(bestMatch.positionInInputText(), bestMatch.typosCount());
    }
}
