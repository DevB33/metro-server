package org.bee.metro.core.document.dto;

public record DocumentUpdatePayload(
        String title,
        String icon,
        String tag,
        String cover
) {
}
