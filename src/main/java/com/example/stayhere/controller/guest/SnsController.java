package com.example.stayhere.controller.guest;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.example.stayhere.controller.guest.sns.KakaoLoginBO;
//import com.example.stayhere.controller.guest.sns.NaverLoginBO;
//import com.github.scribejava.core.model.OAuth2AccessToken;

//@Controller
//@RequestMapping("/sns/*")
public class SnsController {
//	private static final Logger logger = LoggerFactory.getLogger(SnsController.class);
//	
//	@Autowired
//	NaverLoginBO naverLoginBO;
//	
//	@Autowired
//	KakaoLoginBO kakaoLoginBO;
//
//	// 로그인 화면
//	@RequestMapping(value = "/sns_login.do")
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//		String serverUrl = request.getScheme()+"://"+request.getServerName();
//		if(request.getServerPort() != 80) {
//			serverUrl = serverUrl + ":" + request.getServerPort();
//		}
//		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session, serverUrl);
//		String kakaoAuthUrl = kakaoLoginBO.getAuthorizationUrl(session, serverUrl);
//		mav.setViewName("guest/guest_login");
//		mav.addObject("naverAuthUrl", naverAuthUrl);
//		mav.addObject("kakaoAuthUrl", kakaoAuthUrl);
//		
//		return mav;
//	}
//
//	// 네이버 로그인 성공시 callback
//	@RequestMapping(value = "/naverOauth2ClientCallback.do", method = { RequestMethod.GET, RequestMethod.POST })
//	public String naverOauth2ClientCallback(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws Exception {
//		String serverUrl = request.getScheme()+"://"+request.getServerName();
//		if(request.getServerPort() != 80) {
//			serverUrl = serverUrl + ":" + request.getServerPort();
//		}
//		OAuth2AccessToken oauthToken;
//		oauthToken = naverLoginBO.getAccessToken(session, code, state, serverUrl);
//		if(oauthToken == null) {
//			model.addAttribute("msg", "네이버 로그인 access 토큰 발급 오류 입니다.");
//			model.addAttribute("url", "/");
//			return "guest/guest_login";
//		}
//		// 로그인 사용자 정보를 읽어온다
//		String apiResult = naverLoginBO.getUserProfile(oauthToken, serverUrl);
//		JSONParser jsonParser = new JSONParser();
//		Object obj = jsonParser.parse(apiResult);
//		JSONObject jsonObj = (JSONObject) obj;
//		JSONObject response_obj = (JSONObject) jsonObj.get("response");
//		// 프로필 조회
//		String id = (String) response_obj.get("id");
//		// 세션에 사용자 정보 등록
//		session.setAttribute("islogin_r", "Y");
//		session.setAttribute("id", id);
//		return "redirect:/";
//	}
//	
//	// 카카오 로그인 성공시 callback
//	@RequestMapping(value = "/kakaoOauth2ClientCallback.do", method = { RequestMethod.GET, RequestMethod.POST })
//	public String kakaoOauth2ClientCallback(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws Exception {
//		String serverUrl = request.getScheme() + "://" + request.getServerName();
//		if (request.getServerPort() != 80) {
//			serverUrl = serverUrl + ":" + request.getServerPort();
//		}
//		OAuth2AccessToken oauthToken;
//		oauthToken = kakaoLoginBO.getAccessToken(session, code, state, serverUrl);
//		if (oauthToken == null) {
//			model.addAttribute("msg", "카카오 로그인 access 토큰 발급 오류 입니다.");
//			model.addAttribute("url", "/");
//			return "/common/redirect";
//		}
//		// 로그인 사용자 정보를 읽어온다
//		String apiResult = kakaoLoginBO.getUserProfile(oauthToken, serverUrl);
//		JSONParser jsonParser = new JSONParser();
//		Object obj = jsonParser.parse(apiResult);
//		JSONObject jsonObj = (JSONObject) obj;
//		JSONObject response_obj = (JSONObject) jsonObj.get("kakao_account");
//		// 프로필 조회
//		String id = (String) response_obj.get("id");
//		// 세션에 사용자 정보 등록
//		session.setAttribute("islogin_r", "Y");
//		session.setAttribute("id", id);
//		return "redirect:/";
//	}
//
//	// 로그아웃
//	@RequestMapping(value = "/logout.do")
//	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
//		// 세션 삭제
//		session.invalidate();
//		return "redirect:/";
//	}	
}
