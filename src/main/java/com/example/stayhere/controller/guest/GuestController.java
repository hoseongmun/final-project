package com.example.stayhere.controller.guest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.stayhere.controller.FileUtils;
import com.example.stayhere.controller.guest.sns.NaverLoginBO;
import com.example.stayhere.model.guest.dto.GuestDTO;
import com.example.stayhere.model.guest.dto.NaverDTO;
import com.example.stayhere.service.guest.GuestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
@RequestMapping("guest/*")
public class GuestController {
	
	private static final Logger logger = LoggerFactory.getLogger(GuestController.class);
	
	@Inject
	GuestService guestService;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;
	
	@Autowired
    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
        this.naverLoginBO = naverLoginBO;
    }
	
	@RequestMapping("list.do")
	public String list_guest(Model model) {
		List<GuestDTO> list = guestService.list_guest();
		model.addAttribute("list", list);
		return "guest/guest_list";
	}
	
	@RequestMapping("join.do")
	public String insert_guest(Model model) {
		return "guest/guest_join";
	}	

	@RequestMapping("loginCheck")
	public ModelAndView loginCheck(GuestDTO dto, HttpSession session) {
		boolean result = guestService.loginCheck(dto, session);
		ModelAndView mav = new ModelAndView();
		if(result) {
			//로그인 성공시 메인페이지로 이동
			mav.setViewName("main");
		} else {
			//로그인 실패시 로그인 페이지로 리턴
			mav.setViewName("guest/guest_login");
			mav.addObject("message", "error");
		}
		return mav;
	}

	@RequestMapping("insert.do") 
	public ModelAndView insert(GuestDTO dto, ModelAndView mav) { 
		logger.info(dto.toString());
		guestService.insert_Guest(dto); 
		mav.addObject("message","join");
		mav.setViewName("guest/guest_login");
		return mav;
		//return "main"; 
	}

	@RequestMapping("login.do")
	public String login() {
		return "guest/guest_login";
	}
	
	@RequestMapping("logout.do")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		guestService.logout(session);
		mav.setViewName("guest/guest_login");
		mav.addObject("message", "logout");
		return mav;
	}

	@RequestMapping("guest_view/{userid}")
	public ModelAndView view(@PathVariable String userid, ModelAndView mav) {
		mav.setViewName("guest/guest_view");
		mav.addObject("dto", guestService.view_Guest(userid));
		return mav;
	}
	
	@RequestMapping("update/{userid}")
	public ModelAndView update(@PathVariable String userid, ModelAndView mav) {
		mav.setViewName("guest/guest_edit");
		mav.addObject("dto", guestService.view_Guest(userid));
		return mav;
	}
	
	/*
	 * @RequestMapping("edit.do") public ModelAndView edit(GuestDTO dto, HttpSession
	 * session, ModelAndView mav) { String
	 * userid=(String)session.getAttribute("userid"); dto.setUserid(userid); //비밀번호
	 * 체크 boolean result = guestService.checkPw(dto.getUserid(), dto.getPasswd());
	 * if(result) { //비번이 맞으면 회원정보 수정 guestService.update_Guest(dto);
	 * mav.setViewName( "main"); }else { mav.addObject("message", "비밀번호를 확인하세요.");
	 * //수정버튼을 누른 후 에는 입력필드에 나타난 정보가 모두 지워지기 때문에 지워지는 현상을 방지하기 위한 코드 처리
	 * mav.addObject("dto", dto); //날짜는 dto가 아닌 시스템에서 자동처리했기 때문에 날짜도 입력필드에서 지워지는 현상을
	 * 방지하도록 처리 mav.addObject("join_date",
	 * guestService.view_Guest(dto.getUserid()).getJoin_date());
	 * mav.setViewName("main"); } return mav; }
	 */
	@ResponseBody
	@RequestMapping(value ="idCheck.do", produces = "text/plain")
	public String idCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String userid = request.getParameter("userid");
		GuestDTO dto = new GuestDTO();
		dto.setUserid(userid);
		int using_user = guestService.loginLookup(dto);
		String result = "" + using_user; // 숫자를 문자열로 변환
		return result;		
	}
	
	@RequestMapping("edit.do")
	public ModelAndView guest_img(GuestDTO dto, HttpSession session, MultipartFile file, ModelAndView mav) throws Exception {
		System.out.println(dto);
		String userid = (String)session.getAttribute("userid");
		dto.setUserid(userid);
		System.out.println(dto);
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String fileName = null;
		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
		   fileName =  FileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes());   
		   mav.addObject("message","profile");
		} else {//file name dto.get으로 불러와서 저장 null값이면
		   fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}
		dto.setProfile_img(File.separator + "imgUpload" + File.separator + fileName);
		guestService.update_Guest(dto);
		mav.addObject("dto", guestService.view_Guest(userid));
		mav.setViewName("guest/guest_view");
		return mav;
	}	
	
	/* 메일인증 */
	@RequestMapping(value="mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheck(@ModelAttribute GuestDTO dto, HttpServletRequest request, HttpServletResponse response, String email) throws Exception {
		/* 뷰로부터 넘어온 데이터 확인 */
		logger.info("이메일 데이터 전송 확인");
		logger.info("이메일 : " + email);
		//int result = guestService.emailCheck(email);
		//PrintWriter out = response.getWriter();
		//if(result==0) {
		/* 인증키 생성 */
		Random random = new Random();
		int checkNum = random.nextInt(888888)+111111;
		logger.info("인증번호 : " + checkNum);
		HttpSession session = request.getSession();
		String num = Integer.toString(checkNum);
		dto.setEmail(email);
		session.setAttribute("checkNum", checkNum);
		/* 인증키 전송 */
		//guestService.authEmail(response, session, dto);
		guestService.sendEmail(dto, "auth", num);
		//out.close();
		return num;
		//}else {
		//	PrintWriter out2 = response.getWriter();
		//	out2.print("이미 사용된 이메일입니다.");
		//	out2.close();
		//	return "guest/guest_join";
		//}
	}
	
	/* 비밀번호 찾기*/
	@RequestMapping(value="findpw", method=RequestMethod.GET)
	public void findPwGet() throws Exception {
	}
	
	@RequestMapping(value = "findpw", method = RequestMethod.POST)
	public void findPwPOST(@ModelAttribute GuestDTO dto, HttpServletResponse response) throws Exception{
		guestService.findPw(response, dto);
	}	
	
	@RequestMapping(value = "testLogin")
	public String isComplete(HttpSession session) {
		return "/guest/naverlogin"; 
	}

    //로그인 첫 화면 요청 메소드
    @RequestMapping(value = "/naverlogin", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(Model model, HttpSession session) {
        
        /* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO의 getAuthorizationUrl메소드 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        								
        //redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
        System.out.println("네이버:" + naverAuthUrl);
        model.addAttribute("url", naverAuthUrl);

        /* 생성한 인증 URL을 View로 전달 */
        return "guest/guest_login";
    }

    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/callback.do", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
            throws IOException {
        System.out.println("여기는 callback");
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        //로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken);
        System.out.println(naverLoginBO.getUserProfile(oauthToken).toString());
        model.addAttribute("result", apiResult);
        System.out.println("result : "+apiResult);
        /* 네이버 로그인 성공 페이지 View 호출 */
//      JSONObject jsonobj = jsonparse.stringToJson(apiResult, "response");
//      String snsId = jsonparse.JsonToString(jsonobj, "id");
//      String name = jsonparse.JsonToString(jsonobj, "name");
//      UserVO vo = new UserVO();
//      vo.setUser_snsId(snsId);
//      vo.setUser_name(name);
//      System.out.println(name);
//      try {
//          vo = service.naverLogin(vo);
//      } catch (Exception e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
//      session.setAttribute("login",vo);
//      return new ModelAndView("user/loginPost", "result", vo);
        return "guest/naverSuccess";
    }	
	
//	//카카오 로그인페이지 이동
//    @RequestMapping(value="kakao.do")
//    public String kakaoLogin() {
//        StringBuffer loginUrl = new StringBuffer();
//        loginUrl.append("https://kauth.kakao.com/oauth/authorize?client_id=");
//        loginUrl.append("8b61a1ad6f82b03d8425e14339a0f7e3"); 
//        loginUrl.append("&redirect_uri=");
//        loginUrl.append("https://localhost/guest/kakaoLogin"); 
//        loginUrl.append("&response_type=code");
//        return "redirect:"+loginUrl.toString();
//    }
//    //홈페이지 로그인
//    @RequestMapping(value = "/kakaologin.do", method = RequestMethod.GET)
//    public String redirectkakao(@RequestParam(value="code", required = false) String code, HttpSession session) throws IOException {
//            System.out.println(code);
//           return "main";
//            //접속토큰 get
//            String kakaoToken = guestService.getReturnAccessToken(code);
//            //접속자 정보 get
//            Map<String,Object> map = guestService.getUserInfo(kakaoToken);
//            System.out.println("컨트롤러 출력"+map.get("nickname")+map.get("profile_image"));
//            GuestDTO dto =new GuestDTO();
//            dto.setName((String)map.get("nickname"));
//            dto.setProfile_img((String)map.get("profile_image"));
//            session.setAttribute("dto", dto);
//            /*로그아웃 처리 시, 사용할 토큰 값*/
//            session.setAttribute("kakaoToken", kakaoToken);
//        return "redirect:/";
//    }
	
//	@RequestMapping(value = "/memberloginform.do", method = RequestMethod.GET)
//	public ModelAndView memberLoginForm(HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
//		//String naverAuthUrl = naverLoginDTO.getAuthorizationUrl(session);
//		String kakaoUrl = KakaoController.getAuthorizationUrl(session);
//		/* 생성한 인증 URL을 View로 전달 */
//		mav.setViewName("memberloginform");
//		// 네이버 로그인
//		//mav.addObject("naver_url", naverAuthUrl);
//		// 카카오 로그인
//		mav.addObject("kakao_url", kakaoUrl);
//		return mav;
//	}// end memberLoginForm()
//	
//	@RequestMapping(value = "/kakaologin.do", produces = "application/json", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
//			HttpServletResponse response, HttpSession session) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		// 결과값을 node에 담아줌
//		JsonNode node = KakaoController.getAccessToken(code);
//		// accessToken에 사용자의 로그인한 모든 정보가 들어있음
//		JsonNode accessToken = node.get("access_token");
//		// 사용자의 정보
//		JsonNode userInfo = KakaoController.getKakaoUserInfo(accessToken);
//		String kemail = null;
//		String kname = null;
//		String kimage = null;
//		// 유저정보 카카오에서 가져오기 Get properties
//		JsonNode properties = userInfo.path("properties");
//		JsonNode kakao_account = userInfo.path("kakao_account");
//		kemail = kakao_account.path("email").asText();
//		kname = properties.path("nickname").asText();
//		kimage = properties.path("profile_image").asText();
//		session.setAttribute("kemail", kemail);
//		session.setAttribute("kname", kname);
//		session.setAttribute("kimage", kimage);
//		mav.setViewName("main");
//		return mav;
//	}// end kakaoLogin()
//
//	// 네이버 로그인 & 회원정보(이름) 가져오기
//		@RequestMapping(value = "/naverlogin.do", produces = "application/json;charset=utf-8", method = { RequestMethod.GET,
//				RequestMethod.POST })
//		public ModelAndView naverLogin(@RequestParam String code, @RequestParam String state, HttpSession session)
//				throws IOException {
//			ModelAndView mav = new ModelAndView();
//			OAuth2AccessToken oauthToken;
//			oauthToken = NaverDTO.getAccess_Token(session, code, state);
//			// 로그인한 사용자의 모든 정보가 JSON타입으로 저장되어 있음
//			String apiResult = NaverDTO.getUserProfile(oauthToken);
//			// 내가 원하는 정보 (이름)만 JSON타입에서 String타입으로 바꿔 가져오기 위한 작업
//			JSONParser parser = new JSONParser();
//			Object obj = null;
//			try {
//				obj = parser.parse(apiResult);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			JSONObject jsonobj = (JSONObject) obj;
//			JSONObject response = (JSONObject) jsonobj.get("response");
//			String nname = (String) response.get("name");
//			String nemail = (String) response.get("email");
//			String ngender = (String) response.get("gender");
//			String nbirthday = (String) response.get("birthday");
//			String nage = (String) response.get("age");
//			String nimage = (String) response.get("profile_image");
//			// 로그인&아웃 하기위한 세션값 주기
//			session.setAttribute("nname", nname);
//			session.setAttribute("nemail", nemail);
//			session.setAttribute("ngender", ngender);
//			session.setAttribute("nbirthday", nbirthday);
//			session.setAttribute("nage", nage);
//			session.setAttribute("nimage", nimage);
//			// 네이버 로그인 성공 페이지 View 호출
//			mav.setViewName("main");
//			return mav;
//		}// end naverLogin()
	
}
