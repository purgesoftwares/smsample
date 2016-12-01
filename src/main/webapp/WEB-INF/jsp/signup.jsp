<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ZIPPI | Register </title>
    <link rel="stylesheet" href="/resources/css/sivapp-style.css" />
</head>
<body>
    <div id="space"></div>
    <div id="main" class="container-fluid">
        <div class="row">
        	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
         		<a href="/login" class="pull-right" ><spring:message code="user.login"/></a>
         	</div>
            <div class="col-lg-6 col-lg-offset-3
                        col-md-6 col-md-offset-3
                        col-sm-10 col-sm-offset-1
                        col-xs-12 col-xs-offset-0">

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="panel-title"><spring:message code="user.registerReg"/></div>
                    </div>
                    <c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="alert alert-success">${msg}</div>
					</c:if>
                    <div class="panel-body">
                        <form name='loginForm'
                              id="registerForm"
                              method='POST'>
                            <div class="form-group">
                                <label for="firstName"><spring:message code="user.firstName"/></label>
                                <input type="text"
                                       class="form-control"
                                       id="firstName"
                                       name="firstName"
                                       value="${customer.firstName}"
                                       placeholder="First Name" />
                            </div>
                            <div class="form-group">
                                <label for="lastName"><spring:message code="user.lastName"/></label>
                                <input type="text"
                                       class="form-control"
                                       id="lastName"
                                       name="lastName"
                                        value="${customer.lastName}"
                                       placeholder="Last Name" />
                            </div>
                            <div class="form-group">
                                <label for="username"><spring:message code="user.email"/></label>
                                <input type="email"
                                       class="form-control"
                                       id="username"
                                       name="username"
                                       value="${user.username}"
                                       placeholder="Email" />
                            </div>
                            <div class="form-group">
                                <label for="gender"><spring:message code="user.gender"/></label>
                                <select id="gender" 
                                	name="gender"
                                	class="form-control" >
                                	<option value="male">Male</option>
                                	<option value="female">Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="user.pass"/></label>
                                <p class="help-block">
                                    Passwords must be at least 8
                                    characters long, contain uppercase and
                                    lowercase letters, a digit, and a special
                                    character (!@#$%^&*+=._-).
                                </p>
                                <input type="password"
                                       class="form-control"
                                       id="password"
                                       name="password"
                                       placeholder="Password" />
                            </div>
                            <div class="form-group">
                                <label for="confirmNewPassword"><spring:message code="user.confirmPassword"/></label>
                                <input type="password"
                                       class="form-control"
                                       id="confirmNewPassword"
                                       name="confirmNewPassword"
                                       placeholder="Confirm Password" />
                            </div>
                            <div class="text-right">
                                <button type="submit" class="btn btn-primary">
                                    <spring:message code="user.registerReg"/>
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
    <footer class="site-footer">&copy; Copyright - ZIPPI 2016</footer>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/toastr.min.css">
<script src="/resources/js/vendor/jquery-1.11.2.min.js"></script>
<script src="/resources/js/vendor/bootstrap.min.js"></script>
<script src="/resources/js/vendor/add-space.js"></script>
<script src="/resources/js/utils/validator.js"></script>
<script src="/resources/js/vendor/toastr.min.js"></script>
<script>
    $(document).ready(function() {
        var $form = $('#registerForm'),
            $firstName = $('#firstName'),
            $lastName = $('#lastName'),
            $username = $('#username'),
            $password = $('#password'),
            $confirmPassword = $('#confirmNewPassword');
        enforcePolicy($form,
                      $firstName,
                      $lastName,
                      $username,
                      $password,
                      $confirmPassword);
    });
</script>
</body>
</html>