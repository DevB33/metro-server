package org.bee.metro.core.block.api;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.dto.BlockCreationRequest;
import org.bee.metro.core.block.dto.BlockCreationResponse;
import org.bee.metro.global.auth.annotation.Login;
import org.springframework.http.ResponseEntity;
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
                blockCreationRequest.documentId(),
                BlockType.valueOf(blockCreationRequest.type().toUpperCase()),
                blockCreationRequest.upperOrder() + 1
        );
        return ResponseEntity.ok(new BlockCreationResponse(createdBlock.getId()));
    }
}
