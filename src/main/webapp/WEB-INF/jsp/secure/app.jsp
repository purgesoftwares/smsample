<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ZIPPI</title>
    <link rel="stylesheet"
              href="/resources/css/bootstrap.min.css">
        <link rel="stylesheet"
              href="/resources/css/jquery-ui.min.css">
        <link rel="stylesheet"
              href="/resources/css/toastr.min.css">
        <link rel="stylesheet"
              href="/resources/css/jquery-ui.structure.min.css">
        <link rel="stylesheet"
              href="/resources/css/jquery-ui.theme.min.css">
        <link rel="stylesheet"
              href="/resources/fonts/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet"
              href="/resources/fonts/siv/style.css">
        <link rel="stylesheet"
              href="/resources/css/backgrid.css">
        <link rel="stylesheet"
              href="/resources/css/extensions/paginator/backgrid-paginator.css">
        <link rel="stylesheet"
              href="/resources/css/extensions/text-cell/backgrid-text-cell.css">
        <link rel="stylesheet"
              href="/resources/css/extensions/moment-cell/backgrid-moment-cell.min.css">
        <link rel="stylesheet"
              href="/resources/css/imgareaselect-default.css">
        <link rel="stylesheet"
              href="/resources/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet"
            href="/resources/js/vendor/bootstrap-toggle/css/bootstrap-toggle.min.css">
        <link rel="stylesheet"
            href="/resources/js/vendor/select2/select2.css">
        <link rel="stylesheet"
            href="/resources/js/vendor/extensions/select2-cell/backgrid-select2-cell.min.css">
        <link rel="stylesheet"
              href="/resources/css/sivapp-style.css">
        <script type="text/javascript"
                defer
                src="/resources/js/vendor/pre-utils.js"></script>
        <script>var require = {config: function (c) {require = c}}</script>
      <script src="/resources/js/require-config.js"></script>
      <script type="text/javascript"
                data-main="/resources/js/App.js"
                defer
                src="/resources/js/require.js"></script>
        <script type="text/javascript"
                charset="utf-8"
                defer
                data-requirecontext="_"
                data-requiremodule="config"
                src="/resources/js/App.js"></script>
        <style type="text/css">
            body {
                padding-top: 70px;
            }
        </style>
       
    </head>

    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button"
                            class="navbar-toggle collapsed"
                            data-toggle="collapse"
                            data-target="#navbar"
                            aria-expanded="false"
                            aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img class="header-logo"
                              src="/resources/images/logo-civa.png" />
                    </a>
                </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right">
                <li>
                            <a href="/logout">
                                <spring:message code="user.logout"/><span class="sr-only">Logout</span>
                            </a>
                        </li>
              </ul>
            </div>
        </div>
        </nav>
    <div class="container-fluid" id="app_container"></div>
    <footer class="site-footer">&copy; Copyright - ZIPPI 2016</footer>
  </body>
</html>
