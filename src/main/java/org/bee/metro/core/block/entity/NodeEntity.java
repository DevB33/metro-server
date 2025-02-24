package org.bee.metro.core.block.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.core.block.domain.node.Node;

@Entity
@Getter
@Table(name = "NODE")
@NoArgsConstructor
public class NodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String content;

    private String style;

    @Column(name = "node_order")
    private Long order;

    private UUID blockId;

    private UUID documentId;

    @Builder
    public NodeEntity(UUID id, String content, String style, Long order, UUID blockId, UUID documentId) {
        this.id = id;
        this.content = content;
        this.style = style;
        this.order = order;
        this.blockId = blockId;
        this.documentId = documentId;
    }

    public static NodeEntity from(Node node) {
        return NodeEntity.builder()
            .id(node.getId())
            .content(node.getContent())
            .style(convertStyleMapToString(node.getStyle()))
            .order(node.getOrder())
            .blockId(node.getBlockId())
            .documentId(node.getDocumentId())
            .build();
    }

    private static String convertStyleMapToString(Map<String, String> style) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(style);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
