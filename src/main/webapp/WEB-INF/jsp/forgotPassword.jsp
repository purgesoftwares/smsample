<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ZIPPI | Forgot Password </title>
    <link rel="stylesheet" href="/resources/css/sivapp-style.css" />
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
    <div id="space"></div>
    <div id="main" class="container-fluid">
        <div class="row">
        	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
         		<a href="/" class="pull-right" ><spring:message code="user.login"/></a>
         	</div>
            <div class="col-lg-4 col-lg-offset-4
                            col-md-4 col-md-offset-4
                            col-sm-10 col-sm-offset-1
                            col-xs-12 col-xs-offset-0">

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="panel-title"><spring:message code="user.title"/></div>
                    </div>
                    <c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<c:if test="${not empty recaptcha}">
						<div class="alert alert-danger">${recaptcha}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="alert alert-success">${msg}</div>
					</c:if>
                    <div class="panel-body">
                        <form name='forgotPasswordForm'
                              action="<c:url value='/forgot-password' />"
                              method='POST'>
                            <div class="form-group">
                                <label for="username"><spring:message code="user.email"/></label>
                                <input type="email"
                                       class="form-control"
                                       id="email"
                                       name="email"
                                       placeholder="Email" />
                            </div>
                            <div class="text-right">
                                <button type="submit" class="btn btn-primary">
                                    <spring:message code="user.submit"/>
                                </button>
                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer class="site-footer">&copy; Copyright - ZIPPI 2015</footer>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/vendor/jquery-1.11.2.min.js"></script>
<script src="/resources/js/vendor/bootstrap.min.js"></script>
<script src="/resources/js/vendor/add-space.js"></script>
</body>
</html>