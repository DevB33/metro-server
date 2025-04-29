package org.bee.metro.core.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;

@Entity
@Getter
@Table(name = "BLOCK")
@NoArgsConstructor
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private BlockType type;

    @Column(name = "block_order")
    private Long order;

    private UUID documentId;

    private UUID memberId;

    @Convert(converter = NodeConverter.class)
    private Node node;

    @Builder
    public BlockEntity(UUID id, BlockType type, Long order, UUID documentId, UUID memberId) {
        this.id = id;
        this.type = type;
        this.order = order;
        this.documentId = documentId;
        this.memberId = memberId;
    }

    public static BlockEntity from(Block block) {
        return BlockEntity.builder()
                .id(block.getId())
                .type(block.getType())
                .order(block.getOrder())
                .documentId(block.getDocumentId())
                .memberId(block.getMemberId())
                .build();
    }
}
