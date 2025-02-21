package org.bee.metro.core.block.infra;

import java.util.UUID;
import org.bee.metro.core.block.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeJpaRepository extends JpaRepository<NodeEntity, UUID> {
}
