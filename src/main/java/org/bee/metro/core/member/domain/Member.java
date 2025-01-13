package org.bee.metro.core.member.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.bee.metro.core.member.entity.MemberEntity;
import org.bee.metro.core.member.exception.MemberErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
public class Member {

    private UUID id;
    private String name;
    private String email;
    private String avatar;

    private static String ERROR_NAME_IS_BLANK = "이름은 비어있을 수 없습니다.";
    private static String ERROR_EMAIL_IS_BLANK = "이메일은 비어있을 수 없습니다.";
    private static String ERROR_INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다. 현재 이메일: %s";

    @Builder
    public Member(UUID id, String name, String email, String avatar) {
        validateName(name);
        validateEmail(email);

        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public static Member fromEntity(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .build();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException(ERROR_NAME_IS_BLANK, MemberErrorCode.ARGUMENT_IS_NULL);
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException(ERROR_EMAIL_IS_BLANK, MemberErrorCode.ARGUMENT_IS_NULL);
        }

        if (!email.contains("@")) {
            throw new BadRequestException(ERROR_INVALID_EMAIL_FORMAT.formatted(email), MemberErrorCode.INVALID_EMAIL_FORMAT);
        }
    }
}
