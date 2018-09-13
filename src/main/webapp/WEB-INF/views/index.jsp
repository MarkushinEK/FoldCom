<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="UTF-8"/>
        <%@ page contentType="text/html;charset=utf-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <title>Forum</title>
        <style>
           <%@include file='css/bootstrap.css' %>
           <%@include file='css/mystyle.css' %>

           .to-be-changed {
               color: black;
           }
           input[type=checkbox]:checked ~ .to-be-changed {
               color: red;
           }

        </style>
    </head>
    <body>
        <header class="header">
            <center>
                <div><h1>File Commander</h1></div>
            </center>
        </header>

        <center>
            <form action="${folderid}/create" method="POST">
                Name: <input type="text" name="name"/>
                <input type="submit" value="Create">
            </form>
        </center>

        <div>
            <a href="/"><span>Root</span></a>
            <c:forEach items="${path}" var="path">
                <a href="/folder/${path.id}"><span>${path.name}</span></a>
            </c:forEach>
        </div>

        <%
            if (request.getSession().getAttribute("folderForMove") != null) {
        %>

        <div>
            <form action="${folderid}/move" method="GET">
                <input type="submit" value="Add" />
                <span style="font-size: 20px"> ${folderName} </span>
            </form>
        </div>

        <hr>

        <form action="/cancel" method="GET">
        <input type="submit" value="Cancel move condition" />
        </form>

        <%
            }
        %>

        <div class="table">
            <form method="POST">
                    <c:forEach items="${folders}" var="folders">
                        <div>
                            <img src="http://localhost:8080/image/simpleFold.png" width="20" height="20" align="left" vspace="5" hspace="5"/>
                            <div>
                                <input name="folder" type="radio" value="${folders.id}">
                                <span style="font-size: 20px">
                                    ${folders.name}
                                </span>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                <button type="submit" formaction="/folder">Open</button>
                <button type="submit" formaction="${folderid}/delete">Delete</button>
                <button type="submit" formaction="${folderid}/renamepage">Rename</button>

                <%
                    if (request.getSession().getAttribute("folderForMove") == null) {
                %>

                <button type="submit" formaction="${folderid}/moveCondition">Move</button>

                <%
                    }
                %>

            </form>

        <div>

    </body>
</html>