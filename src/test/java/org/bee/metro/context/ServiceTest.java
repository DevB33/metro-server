package org.bee.metro.context;

import org.bee.metro.core.auth.application.AuthService;
import org.bee.metro.core.document.application.DocumentService;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.bee.metro.core.member.application.MemberService;
import org.bee.metro.core.member.domain.MemberRepository;
import org.bee.metro.global.auth.jwt.AccessTokenProvider;
import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    protected AccessTokenProvider accessTokenProvider;

    @Autowired
    protected RefreshTokenProvider refreshTokenProvider;

    @Autowired
    protected DocumentService documentService;

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected DocumentRepository documentRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }
}
