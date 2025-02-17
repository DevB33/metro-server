package org.bee.metro.core.document.dto;

public record DocumentUpdateRequest(
    String type,
    String value
) {
}
