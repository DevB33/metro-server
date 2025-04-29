package org.bee.metro.core.block.domain.block;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InnerNode {
    private final String content;
    private final Map<String, String> style;

    @Builder
    public InnerNode(String content, Map<String, String> style) {
        this.content = content;
        this.style = style;
    }
}
