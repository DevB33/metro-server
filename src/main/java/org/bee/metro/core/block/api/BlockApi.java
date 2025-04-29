package org.bee.metro.core.block.api;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.dto.BlockCreationRequest;
import org.bee.metro.core.block.dto.BlockCreationResponse;
import org.bee.metro.core.block.dto.BlockDeletionRequest;
import org.bee.metro.core.block.dto.BlockNodesUpdateRequest;
import org.bee.metro.core.block.dto.BlockOrderUpdateRequest;
import org.bee.metro.core.block.dto.BlockTypeUpdateRequest;
import org.bee.metro.core.block.dto.DetailBlockPayload;
import org.bee.metro.core.block.dto.DetailBlocksRequest;
import org.bee.metro.core.block.dto.DetailBlocksResponse;
import org.bee.metro.global.auth.annotation.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocks")
@RequiredArgsConstructor
public class BlockApi {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<BlockCreationResponse> createBlock(
            @Login UUID memberId, @RequestBody BlockCreationRequest blockCreationRequest
    ) {
        Block createdBlock = blockService.createBlock(
                memberId,
                blockCreationRequest.noteId(),
                BlockType.valueOf(blockCreationRequest.type().toUpperCase()),
                blockCreationRequest.upperOrder() + 1,
                blockCreationRequest.nodes()
        );
        return ResponseEntity.ok(new BlockCreationResponse(createdBlock.getId()));
    }

    @GetMapping
    public ResponseEntity<DetailBlocksResponse> findBlocks(@RequestBody DetailBlocksRequest detailBlocksRequest) {
        List<DetailBlockPayload> detailBlockPayloads = blockService.findByDocumentId(detailBlocksRequest.noteId());
        return ResponseEntity.ok(new DetailBlocksResponse(detailBlockPayloads));
    }

    @PatchMapping("/{blockId}/nodes")
    public ResponseEntity<Void> updateNodes(
            @Login UUID memberId,
            @PathVariable(name = "blockId") UUID blockId,
            @RequestBody BlockNodesUpdateRequest blockNodesUpdateRequest
    ) {
        blockService.updateNodes(memberId, blockId, blockNodesUpdateRequest.nodes());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{blockId}/type")
    public ResponseEntity<Void> updateBlockType(
            @Login UUID memberId,
            @PathVariable(name = "blockId") UUID blockId,
            @RequestBody BlockTypeUpdateRequest request
    ) {
        blockService.updateBlockType(
                memberId,
                blockId,
                BlockType.valueOf(request.type().toUpperCase())
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/order")
    public ResponseEntity<Void> updateBlockOrder(
            @Login UUID memberId, @RequestBody BlockOrderUpdateRequest blockOrderUpdateRequest
    ) {
        blockService.updateBlocksOrder(
                blockOrderUpdateRequest.noteId(),
                memberId,
                blockOrderUpdateRequest.startOrder(),
                blockOrderUpdateRequest.endOrder(),
                blockOrderUpdateRequest.upperOrder()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBlocksInRange(
            @Login UUID memberId, @RequestBody BlockDeletionRequest blockDeletionRequest
    ) {
        blockService.deleteBlockInRange(
                blockDeletionRequest.noteId(),
                memberId,
                blockDeletionRequest.startOrder(),
                blockDeletionRequest.endOrder()
        );
        return ResponseEntity.ok().build();
    }
}
