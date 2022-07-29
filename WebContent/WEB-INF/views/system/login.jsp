<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>酒店管理系统后台</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <link rel="stylesheet" media="screen" href="../resources/admin/login/css/style.css">
  <link rel="stylesheet" type="text/css" href="../resources/admin/login/css/reset.css">
<body>

<div id="particles-js">
		<div class="login" style="display: block;">
			<div class="login-top">
				登录
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="../resources/admin/login/images/name.png"></div>
				<div class="login-center-input">
					<input type="text" name="username" id="username" placeholder="请输入您的用户名" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的用户名&#39;">
					<div class="login-center-input-text">用户名</div>
				</div>
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="../resources/admin/login/images/password.png"></div>
				<div class="login-center-input">
					<input type="password" name="password" id="password" placeholder="请输入您的密码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的密码&#39;">
					<div class="login-center-input-text">密码</div>
				</div>
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="../resources/admin/login/images/cpacha.png"></div>
				<div class="login-center-input">
					<input style="width:50%;" type="text" name="cpacha" id="cpacha"  placeholder="请输入验证码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入验证码&#39;">
					<div class="login-center-input-text">验证码</div>
					<img id="cpacha-img" title="点击切换验证码" style="cursor:pointer;" src="get_cpacha?vl=4&w=150&h=40&type=loginCpacha" width="110px" height="30px" onclick="changeCpacha()">
				</div>
			</div>
			<div class="login-button">
				登录
			</div>
		</div>
</div>
<!-- scripts -->
<script src="../resources/admin/login/js/app.js"></script>
<script src="../resources/admin/login/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	
	function changeCpacha(){
		$("#cpacha-img").attr("src",'get_cpacha?vl=4&w=150&h=40&type=loginCpacha&t=' + new Date().getTime());
	}
		document.querySelector(".login-button").onclick = function(){
				var username = $("#username").val();
				var password = $("#password").val();
				var cpacha = $("#cpacha").val();
				if(username == '' || username == 'undefined'){
					alert("请填写用户名！");
					return;
				}
				if(password == '' || password == 'undefined'){
					alert("请填写密码！");
					return;
				}
				if(cpacha == '' || cpacha == 'undefined'){
					alert("请填写验证码！");
					return;
				}

				$.ajax({
					url:'login',
					data:{username:username,password:password,cpacha:cpacha},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.type == 'success'){
							window.parent.location = 'index';
						}else{
							/*removeClass(document.querySelector(".login"), "active");*/
							/*document.querySelector(".login").style.display = "block";*/
							alert(data.msg);
							changeCpacha();
						}
					}
				});
		}
</script>
</body></html>