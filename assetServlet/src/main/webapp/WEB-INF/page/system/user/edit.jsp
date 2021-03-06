<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/inc/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<jsp:include page="/WEB-INF/inc/common.jsp"></jsp:include>
</head>
<body>
	<article class="page-container">
		<form class="form form-horizontal" id="form" method="post" action="${pageContext.request.contextPath}${requestScope.record != null ?  '/system/user.do?method=update' :'/system/user.do?method=insert'  }">
			<!-- 隐藏表单 -->
			<c:if test="${requestScope.record != null }">
				<input type="hidden" name="id" value="${requestScope.record.id }">
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${requestScope.record.name }" placeholder="" id="name" name="name">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>密码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="password" class="input-text" autocomplete="off" value="${requestScope.record.pwd }" placeholder="" id="pwd" name="pwd">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
				<div class="formControls col-xs-8 col-sm-9 skin-minimal">
					<div class="radio-box">
						<input name="sex" type="radio" id="sex-1" value="1" ${requestScope.record.sex == 1 ? 'checked' : '' }> <label for="sex-1">男</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="sex-2" name="sex" value="2" ${requestScope.record.sex == 2 ? 'checked' : '' }> <label for="sex-2">女</label>
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>生日：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${requestScope.record.birth }" placeholder="" id="birth" name="birth">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${requestScope.record.phone }" placeholder="" id="phone" name="phone">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮箱：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${requestScope.record.email }" placeholder="@" name="email" id="email">
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</article>

	<script type="text/javascript">
		//日期控件
		laydate.render({
			elem : '#birth' //指定元素
		});

		$("#form").validate({
			rules : {
				name : {
					required : true,
					minlength : 2,
					maxlength : 50
				},
				pwd:{
					required : true,
					minlength : 6,
				},
				sex : {
					required : true,
				},
				birth : {
					required : true,
					dateISO:true
				},
				phone : {
					required : true,
					minlength : 11,
					maxlength : 11,
					digits:true
				},
				email : {
					required : true,
					email : true,
				} 
			},
			onkeyup : false,
			focusCleanup : true,
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					type : form.method,
					url : form.action,
					success : function(data) {
						commonreuslt(data);
					}
				});
			}
		});
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>