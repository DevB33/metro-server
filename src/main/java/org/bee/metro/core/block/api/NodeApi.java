package org.bee.metro.core.block.api;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodes")
@RequiredArgsConstructor
public class NodeApi {

    private final BlockService blockService;

}
