package org.hmcampoverde.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {

    EMPLOYEE("Employee"),
    AGENT("Agent");

    private String description;

}
