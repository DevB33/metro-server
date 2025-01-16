package org.bee.metro.core.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService implements OAuthService {

    @Value("${auth.google.client-id}")
    private String clientId;

    @Value("${auth.google.client-secret}")
    private String clientSecret;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String HTTPS = "https";
    private static final String TOKEN_URL_HOST = "oauth2.googleapis.com";
    private static final String TOKEN_URL_PATH = "/token";
    private static final String PROFILE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String REDIRECT_URI = "http://localhost:8080/auth/callback";

    @Override
    public String getAccessToken(String authorizationCode, String state) {
        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme(HTTPS)
                        .host(TOKEN_URL_HOST)
                        .path(TOKEN_URL_PATH)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", authorizationCode)
                        .queryParam("redirect_uri", REDIRECT_URI)
                        .queryParam("grant_type", "authorization_code")
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new BadRequestException("구글 토큰을 가져오는데 실패했습니다.", AuthErrorCode.TOKEN_FETCH_FAILED);
        }
    }

    @Override
    public MemberCreationPayload getMemberInfo(String accessToken) {
        String response = webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return new MemberCreationPayload(
                    "g-" + jsonNode.get("id").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("email").asText(),
                    jsonNode.get("picture").asText()
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestException("구글 사용자 정보를 가져오는데 실패했습니다.", AuthErrorCode.PROFILE_FETCH_FAILED);
        }
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.GOOGLE;
    }
}
