package org.bee.metro.core.block.dto;

import java.util.Map;

public record NodeStyleUpdateRequest(
        Map<String, String> style
) {
}
