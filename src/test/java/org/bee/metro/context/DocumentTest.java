package org.bee.metro.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bee.metro.core.auth.api.AuthApi;
import org.bee.metro.core.auth.application.AuthService;
import org.bee.metro.core.document.api.DocumentApi;
import org.bee.metro.core.document.application.DocumentService;
import org.bee.metro.document.config.DocumentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {AuthApi.class, DocumentApi.class})
@Import({DocumentConfig.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class DocumentTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected DocumentService documentService;

    @MockitoBean
    protected AuthService authService;

    @Autowired
    protected ObjectMapper objectMapper;
}
