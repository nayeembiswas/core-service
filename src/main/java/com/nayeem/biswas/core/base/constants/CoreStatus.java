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
public enum CoreStatus {
    DRAFT("Draft"),
    PUBLISHED("Published"),
    ARCHIVED("Archived"),
    PENDING("Pending"),
    PAID("Paid"),
    UNPAID("Unpaid"),
    FAILED("Failed"),
    CANCEL("Cancel"),
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    UNLIMITED("Unlimited");

    public final String value;

    CoreStatus(String value) {
        this.value = value;
    }
}
