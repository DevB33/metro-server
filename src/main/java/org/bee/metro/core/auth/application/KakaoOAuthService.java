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
public class KakaoOAuthService implements OAuthService {

    @Value("${auth.kakao.client-id}")
    private String clientId;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String REDIRECT_URI = "http://localhost:8080/auth/callback";

    @Override
    public String getAccessToken(String authorizationCode, String state) {
        String response = webClient.post()
                .uri(TOKEN_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=authorization_code&client_id=" + clientId
                        + "&redirect_uri=" + REDIRECT_URI + "&code=" + authorizationCode)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new BadRequestException("카카오 토큰을 가져오는데 실패했습니다.", AuthErrorCode.TOKEN_FETCH_FAILED);
        }
    }

    @Override
    public MemberCreationPayload getMemberInfo(String accessToken) {
        String response = webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return new MemberCreationPayload(
                    "k-" + jsonNode.get("id").asText(),
                    jsonNode.get("properties").get("nickname").asText(),
                    jsonNode.get("kakao_account").get("email").asText(),
                    jsonNode.get("properties").get("profile_image").asText()
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestException("카카오 회원정보를 가져오는데 실패했습니다.", AuthErrorCode.PROFILE_FETCH_FAILED);
        }
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }
}
