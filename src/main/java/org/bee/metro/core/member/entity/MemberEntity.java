package org.bee.metro.core.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.global.entity.BaseEntity;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    private UUID id;

    private String name;

    private String email;

    private String avatar;

    @Builder
    public MemberEntity(UUID id, String name, String email, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public static MemberEntity from(UUID id, String name, String email, String avatar) {
        return MemberEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .avatar(avatar)
                .build();
    }
}
