package org.bee.metro.core.block.api;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.block.dto.NodeContentUpdateRequest;
import org.bee.metro.core.block.dto.NodeCreationRequest;
import org.bee.metro.global.auth.annotation.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PatchMapping("/{nodeId}/content")
    public ResponseEntity<Void> updateNodeContent(
            @Login UUID memberId,
            @PathVariable(name = "nodeId") UUID nodeId,
            @RequestBody NodeContentUpdateRequest request
    ) {
        blockService.updateNodeContent(
                nodeId,
                memberId,
                request.content()
        );
        return ResponseEntity.ok().build();
    }
}
