package org.bee.metro.core.block.infra;

import java.util.UUID;
import org.bee.metro.core.block.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, UUID> {
}
