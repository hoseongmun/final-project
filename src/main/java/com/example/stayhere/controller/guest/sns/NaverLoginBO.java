package com.example.stayhere.controller.guest.sns;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.stayhere.controller.guest.NaverLoginApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Component
public class NaverLoginBO {

    /* 인증 요청문을 구성하는 파라미터 */
	//client_id: 애플리케이션 등록 후 발급받은 클라이언트 아이디
	//response_type: 인증 과정에 대한 구분값, code로 값이 고정
	//redirect_uri: 네이버 로그인 인증의 결과를 전달받을 콜백 URL(URL 인코딩) 애플리케이션을 등록할 때 Callback URL에 설정한 정보
	//state: 애플리케이션이 생성한 상태 토큰
    private final static String NAVER_CLIENT_ID = "tvpmp5NFQxDcEEN45i4P"; // 클라이언트 아이디
    private final static String NAVER_CLIENT_SECRET = "pmkx4QFc4O"; // 클라이언트 시크릿
    private final static String NAVER_REDIRECT_URI = "http://localhost/guest/callback.do";
    private final static String SESSION_STATE = "oauth_state";
    /* 프로필 조회 API URL */
    private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
    
    //네이버 아이디로 인증 url 생성
	public String getAuthorizationUrl(HttpSession session) {
		String state = generateRandomString();
		setSession(session, state);
		//OAuth20Service oauthService = new ServiceBuilder().apiKey(NAVER_CLIENT_ID).apiSecret(NAVER_CLIENT_SECRET).callback(serverUrl + NAVER_REDIRECT_URI).state(state).build(NaverOAuthApi20.instance());
		OAuth20Service oauthService = new ServiceBuilder()
				.apiKey(NAVER_CLIENT_ID)
				.apiSecret(NAVER_CLIENT_SECRET)
				.callback(NAVER_REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		return oauthService.getAuthorizationUrl();
	}

	//네이버 아이디로 callback 처리, accesstoken 값 획득
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
		String sessionState = getSession(session);
		if (StringUtils.pathEquals(sessionState, state)) {
//			OAuth20Service oauthService = new ServiceBuilder().apiKey(NAVER_CLIENT_ID).apiSecret(NAVER_CLIENT_SECRET).callback(serverUrl + NAVER_REDIRECT_URI).state(state).build(NaverOAuthApi20.instance());
//			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			OAuth20Service oauthService = new ServiceBuilder()
					.apiKey(NAVER_CLIENT_ID)
					.apiSecret(NAVER_CLIENT_SECRET)
					.callback(NAVER_REDIRECT_URI)
					.state(state)
					.build(NaverLoginApi.instance());
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
            return accessToken;
		}
		return null;
	}

	//accesstoken 으로 네이버 사용자 프로필 api 호출함
	public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
		OAuth20Service oauthService =new ServiceBuilder()
                .apiKey(NAVER_CLIENT_ID)
                .apiSecret(NAVER_CLIENT_SECRET)
                .callback(NAVER_REDIRECT_URI).build(NaverLoginApi.instance());

            OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
        oauthService.signRequest(oauthToken, request);
        Response response = request.send();
        return response.getBody();
//		OAuth20Service oauthService = new ServiceBuilder().apiKey(NAVER_CLIENT_ID).apiSecret(NAVER_CLIENT_SECRET).callback(serverUrl + NAVER_REDIRECT_URI).build(NaverOAuthApi20.instance());
//		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
//		oauthService.signRequest(oauthToken, request);
//		Response response = request.send();
//		return response.getBody();
	}
	
	//세션 유효성 검증용 난수 생성
	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	//세션에 데이터 저장
	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
	}

	//세션에서 값 가져오기
	private String getSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_STATE);
	}
}
