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
public class NaverOAuthService implements OAuthService {

    @Value("${auth.naver.client-id}")
    private String clientId;

    @Value("${auth.naver.client-secret}")
    private String clientSecret;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
    private static final String PROFILE_URL = "https://openapi.naver.com/v1/nid/me";

    @Override
    public String getAccessToken(String authorizationCode, String state) {
        String response = webClient.get()
                .uri(uriBUilder -> uriBUilder
                        .host(TOKEN_URL)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("state", state)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new BadRequestException("네이버 토큰을 가져오는데 실패했습니다.", AuthErrorCode.TOKEN_FETCH_FAILED);
        }
    }

    @Override
    public MemberCreationPayload getMemberInfo(String accessToken) {
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .host(PROFILE_URL)
                        .build()
                )
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return new MemberCreationPayload(
                    jsonNode.get("response").get("name").asText(),
                    jsonNode.get("response").get("email").asText(),
                    jsonNode.get("response").get("profile_image").asText()
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestException("네이버 회원 정보를 가져오는데 실패했습니다.", AuthErrorCode.PROFILE_FETCH_FAILED);
        }
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

}
