package org.bee.metro.core.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.global.entity.BaseEntity;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String email;

    private String avatar;

    private String oauthId;

    @Builder
    public MemberEntity(UUID id, String name, String email, String avatar, String oauthId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.oauthId = oauthId;
    }

    public static MemberEntity from(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .avatar(member.getAvatar())
                .oauthId(member.getOauthId())
                .build();
    }
}
