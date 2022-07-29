<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="Keywords" content="酒店管理系统">
  <meta name="Description" content="酒店管理系统">
  <title>酒店管理系统注册页面</title>
  <link href="../resources/home/css/login.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/register.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/index.css" type="text/css" rel="Stylesheet" />
  <style>
    #ad>ul {
       margin:0;
    }
  </style>
</head>
<body>
  <!--头部-->
  <header>
    <div>
      <a href="index"><img src="../resources/home/images/logo-1.png"> </a> <span>注册</span>
    </div>

  </header>
  <!--中间部分-->
  <div id="reg">
         <!---温馨提示-->
		 <div class="msg">
			 <div class="panel">
    <form id="user_info">
      <div class="form-group">
        <label for="uname">用户名：</label>
        <input required minlength="2" maxlength="9" type="text" placeholder="请输入用户名" autofocus name="name" id="uname"/>
        <span class="msg-default">用户名长度在2到9位之间</span>
      </div>
      <div class="form-group">
        <label for="upwd">密码：</label>
        <input type="password" minlength="6" maxlength="12" placeholder="请输入密码" name="password" id="upwd"/>
        <span class="msg-default hidden">密码长度在6到12位之间</span>
      </div>
      <div class="form-group">
        <label for="upwd2">确认密码：</label>
        <input type="password" placeholder="再次输入密码" id="upwd2"/>
        <span class="msg-default hidden">密码长度在6到12位之间</span>
      </div>
      <div class="form-group">
        <label for="uphone">手机：</label>
        <input required type="tel" placeholder="请输入手机号码" name="mobile" maxlength="11" id="uphone"/>
        <span class="msg-default hidden">请输入合法的手机号码</span>
      </div>
      <div>
        <div class="form-group">
          <label></label>
          <input type="button" value="提交注册信息" id="btn_reg" style="cursor:pointer;" />
        </div>
      </div>

    </form>
  </div>
			 <div id="ad">
               <div class="login">
                   已有账号，去 <a href="login">登录</a>
               </div>
				
			 </div>
		 </div>
  </div>
   <%@include file="../common/footer.jsp"%>
	  <script src="../resources/home/js/jquery-1.11.3.js"></script>
<script>
  /*1.对用户名进行验证*/
  var login=0;
  uname.onblur = function(){
    var val=this.value;
    if(this.validity.valueMissing){
      this.nextElementSibling.innerHTML = '用户名不能为空';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('用户名不能为空');
    }else if(this.validity.tooShort){
      this.nextElementSibling.innerHTML = '用户名不能少于2位';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('用户名不能少于2位');
    }else {
      this.nextElementSibling.innerHTML = '用户名格式正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
      this.setCustomValidity('');
    }

  }


  //2.对密码进行验证
  upwd.onfocus = function(){
    this.nextElementSibling.innerHTML = '密码至少为6位数字或者字符';
    this.nextElementSibling.className = 'msg-default';
    login=0;
  }
  upwd.onblur = function(){
    if(upwd.value == '' || upwd.value.length < 6){
      this.nextElementSibling.innerHTML = '密码至少为6位数字或者字符';
	  this.nextElementSibling.className = 'msg-default';
      login=0;
    }else{
		this.nextElementSibling.innerHTML = '输入正确';
		this.nextElementSibling.className = 'msg-success';
		login=1;
		this.setCustomValidity('');
	}
      
  }

  uphone.onblur = function(){
	var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if(this.validity.valueMissing){
      this.nextElementSibling.innerHTML = '电话号码不能为空';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码不能为空');
    }else if(this.validity.typeMismatch){
      this.nextElementSibling.innerHTML = '电话号码格式不正确';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码格式不正确');
    }else if(!myreg.test(uphone.value)){
		this.nextElementSibling.innerHTML = '电话号码格式不正确';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码格式不正确');
	}
	else {
      this.nextElementSibling.innerHTML = '格式正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
      this.setCustomValidity('');
    }
  }
  uphone.onfocus = function(){
    this.nextElementSibling.innerHTML = '请输入合法的手机号码';
    this.nextElementSibling.className = 'msg-default';
    login=0;
  }
  //确认密码
  upwd2.onblur=function() {
    if (upwd2.value != upwd.value) {
      this.nextElementSibling.innerHTML = '两次密码输入不一致';
      login=0;
      this.nextElementSibling.className = 'msg-error';
    } else if (upwd2.value == upwd.value) {
      this.nextElementSibling.innerHTML = '输入正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
    }
  }
  $('#btn_reg').click(function(){
    //表单序列化，获得所有的用户输入
    if ($('#uname').val().length<2){
      alert("用户名不符合要求！");
      return;
    }
    if ($('#upwd').val().length<6){
      alert("密码长度不符合要求！");
      return;
    }
    if ($('#uphone').val().length<11){
      alert("手机号不符合要求！");
      return;
    }
    if (upwd2.value != upwd.value){
    	return;
    }
    var data = $('#user_info').serialize();

    //异步提交请求数据
    $.ajax({
      type: 'POST',
      dataType:'json',
      url: 'reg',
      data: data,
      success: function(result){
        if(result.type=='success'){
          alert('注册成功！');
          location.href='login';
        }else {
          alert(result.msg)
        }
      }
    });
  })

</script>	

</body>
</html>
