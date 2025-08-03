package org.bee.metro.core.block.domain.block;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class InnerNode {
    private final String content;
    private final String type;
    private final Map<String, String> style;

    @Builder
    public InnerNode(String content, String type, Map<String, String> style) {
        this.content = content;
        this.type = type;
        this.style = style;
    }
}
