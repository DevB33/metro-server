package org.bee.metro.core.block.api;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.block.dto.NodeCreationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodes")
@RequiredArgsConstructor
public class NodeApi {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<Void> createNode(@RequestBody NodeCreationRequest nodeCreationRequest) {
        blockService.createNode(
                nodeCreationRequest.blockId(),
                nodeCreationRequest.documentId(),
                nodeCreationRequest.content(),
                nodeCreationRequest.order(),
                nodeCreationRequest.style()
        );
        return ResponseEntity.ok().build();
    }
}
