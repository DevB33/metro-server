package org.bee.metro.core.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public NodeEntity(UUID id, String content, String style, Long order, UUID blockId) {
        this.id = id;
        this.content = content;
        this.style = style;
        this.order = order;
        this.blockId = blockId;
    }
}
