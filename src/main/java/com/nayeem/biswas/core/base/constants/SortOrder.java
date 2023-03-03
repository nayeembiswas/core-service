package com.nayeem.biswas.core.base.constants;

import lombok.Getter;
import lombok.ToString;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 06, 2023
 */

@Getter
@ToString
public enum SortOrder {
    ASC("asc"),
    DESC("desc");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }
}
