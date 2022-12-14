<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../include/header.jsp"%>
<title>GUEST login</title>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<style type="text/css">
.form-control{
	width: 296px;
	height: 40px;
}

.btn{
	width: 296px;
	height: 40px;
	background-color: #ffcd4a;
	border-color: ffcd4a;
}

a {text-decoration: none;}
</style>
<script type="text/javascript">
window.onload = function() {
	$("#login").click(function(){
		var userid=$("#userid").val(); //태그의 value 속성값
		var passwd=$("#passwd").val();
		if(userid==""){
			alert("아이디를 입력하세요.");
			$("#userid").focus(); //입력 포커스 이동
			return; //함수 종료
		}
		if(passwd==""){
			alert("비밀번호를 입력하세요.");
			$("#passwd").focus();
			return;
		}
		//폼 데이터를 서버로 제출
		document.form1.action="${path}/guest/loginCheck";
		document.form1.submit();
	});
}
</script>
</head>
<body class="d-flex flex-column h-100">
 <main class="flex-shrink-0">
  <!-- nav -->
	<%@ include file="../include/navbar.jsp" %>
  <!-- 본문영역-->
  <section class="py-5" id="features">
	<div class="container px-5 my-5" align="center">

 <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>

	<form name="form1">
	<h2>GUEST LOGIN</h2>	
	<br>
	<input class="form-control me-2" type="text" placeholder="id" name="userid" id="userid">
	<br>
	<input class="form-control me-2" type="password" placeholder="password" name="passwd" id="passwd">
	<br>
	<input type="button" class="btn btn-primary mb-0" id="login" value="login">
	</form>
    <br>
    <div class="form-group row">
    	<div class="or-seperator"><b>or</b></div>
    </div>
    <br>
    <div id="kakao_id_login" style="text-align: center">
    	<a href="${kakao_url}">
        <img width="296" height="45" src="${path}/resources/images/kakao_login.png" /></a>
        </div>
		<%-- <a href="${kakaoAuthUrl}">  
        	<img width="296" height="45" src="${path}/resources/images/kakao_login.png" />
        </a> --%>
    <br>
    <div id="naver_id_login" style="text-align: center">
    	<a href="${url}"><img width="296" height="45" src="${path}/resources/images/naver_login.png" /></a>
	</div>
		<%-- <a href="${naverAuthUrl}">
        	<img width="296" height="45" src="${path}/resources/images/naver_login.png" />
        </a> --%> 
	<c:if test="${message == 'join' }"><div style="color:blue; font-size: 10px;">로그인 하신 후 사용하세요.</div></c:if>
	<c:if test="${message == 'error' }">
	 <% out.println("<script>"); 
	 out.println("alert('아이디 또는 비밀번호가 일치하지 않습니다.');"); 
	 out.println("</script>");%>
	</c:if>
	<c:if test="${message == 'logout' }">
	 <% out.println("<script>"); 
	 out.println("alert('로그아웃 완료.');"); 
	 out.println("</script>");%>
	 </c:if>	 

	<br>
	<a href="${path}/guest/findpw.do">비밀번호를 잊어버리셨나요?</a>
	
	</div>
  </section>
 </main>
 <!--footer -->
 <%@ include file="../include/footer.jsp" %>
</body>
</html>