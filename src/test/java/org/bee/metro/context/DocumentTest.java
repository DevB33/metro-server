package org.bee.metro.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.bee.metro.core.auth.api.AuthApi;
import org.bee.metro.core.auth.application.AuthService;
import org.bee.metro.core.block.api.BlockApi;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.document.api.DocumentApi;
import org.bee.metro.core.document.application.DocumentService;
import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.bee.metro.global.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {AuthApi.class, DocumentApi.class, BlockApi.class})
@Import({SecurityConfig.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class DocumentTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RefreshTokenProvider refreshTokenProvider;

    @MockitoBean
    protected BlockService blockService;

    @MockitoBean
    protected DocumentService documentService;

    @MockitoBean
    protected AuthService authService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected final UUID randomId = UUID.randomUUID();
    protected String accessToken;

    @BeforeEach
    void setupSecurityContext() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                randomId,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        TestSecurityContextHolder.setContext(context);

        this.accessToken = "Bearer " + refreshTokenProvider.generateToken(randomId);
    }
}
