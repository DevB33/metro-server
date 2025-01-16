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

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String PROFILE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String REDIRECT_URI = "http://localhost:3000/auth/callback";

    @Override
    public String getAccessToken(String authorizationCode, String state) {
        String response = webClient.post()
                .uri(TOKEN_URL)
                .bodyValue("client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + authorizationCode
                        + "redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code")
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
