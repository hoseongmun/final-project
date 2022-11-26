<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
  src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js"
  charset="utf-8"></script>
<!-- 헤더에 공통 스크립트 또는 스타일시트 추가하기 -->
<%@ include file="../include/header.jsp"%>
<!-- 그외 페이지별 들어갈 script & css 추가 영역  start-->
<style type="text/css">
html, div, body, h3 {
  margin: 0;
  padding: 0;
}

h3 {
  display: inline-block;
  padding: 0.6em;
}
</style>
<script type="text/javascript">
  $(document).ready(function() {
    var name = ${result}.response.name;
    var email = ${result}.response.email;
    $("#name").html("환영합니다. "+name+"님");
    $("#email").html(email);
    });
  //location.href = "${pageContext.request.contextPath}/";
</script>
<!-- 그외 페이지별 들어갈 script & css 추가 영역 end -->
<title>STAYHERE</title>

</head>
<body class="d-flex flex-column">
	<!-- nav_search_bar 는 검색창 노출화면 -->
	<!-- 폴더를 만들어서 적용할시 ../ 으로 변경 -->
<%-- 	<%@ include file="./include/nav_search_bar.jsp"%> --%>
	<%@ include file="../include/navbar.jsp"%>
	
	<!-- 컨텐츠 수정 영역 start -->
	
 <div
    style="background-color: #15a181; width: 100%; height: 50px; text-align: center; color: white;">
    <h3>Naver_Login Success</h3>
  </div>
  <br>
  <h2 style="text-align: center" id="name"></h2>
  <h4 style="text-align: center" id="email"></h4>
  <script>
    $(function () {
      $("body").hide();
      $("body").fadeIn(1000);  // 1초 뒤에 사라 지자 
     
      setTimeout(function(){$("body").fadeOut(1000);},1000);
      setTimeout(function(){location.href= "${pageContext.request.contextPath}/"},2000);
// 2초 뒤에 메인 화면 으로 가자  
    
    })
  </script>	
	
	<!-- 컨텐츠 수정 영역 end -->
	
	<!-- footer -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>