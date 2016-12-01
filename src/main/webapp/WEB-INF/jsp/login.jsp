<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SIV | Login </title>
    <link rel="stylesheet" href="/resources/css/sivapp-style.css" />
</head>
<body>
    <div id="space"></div>
    <div id="main" class="container-fluid">
        <div class="row">
            <%--<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <a href="/signup" class="pull-right" >Sign Up</a>
            </div>--%>
            <div class="col-lg-4 col-lg-offset-4
                            col-md-4 col-md-offset-4
                            col-sm-10 col-sm-offset-1
                            col-xs-12 col-xs-offset-0">

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="panel-title">ZIPPI Login</div>
                    </div>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="alert alert-success">${msg}</div>
                    </c:if>
                    <div class="panel-body">
                        <form name='loginForm'
                              action="<c:url value='/login' />"
                              method='POST'>
                            <div class="form-group">
                                <label for="username">Email</label>
                                <input type="email"
                                       class="form-control"
                                       id="username"
                                       name="username"
                                       placeholder="Email" />
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password"
                                       class="form-control"
                                       id="password"
                                       name="password"
                                       placeholder="Password" />
                            </div>
                            <div class="form-group">
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                    <label>
                                        <input type="checkbox" name="remember-me">
                                        Remember Me
                                    </label>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 text-right">
                                    <a href="/forgot-password" alt="Forgot Password" >Forgot Password?</a>
                                </div>
                            </div>
                            <div class="text-right">
                                <button type="submit" class="btn btn-primary">
                                    Login
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
<script src="/resources/js/vendor/jquery-1.11.2.min.js"></script>
<script src="/resources/js/vendor/bootstrap.min.js"></script>
<script src="/resources/js/vendor/add-space.js"></script>
</body>
</html>