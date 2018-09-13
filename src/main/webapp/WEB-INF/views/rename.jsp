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
            <form action="rename/${folder.id}" method="POST">
                Name: <input type="text" name="name"/>
                <input type="submit" value="Rename">
            </form>
        </center>

        <div class="table">
            <div>
                <center>
                    <img src="http://localhost:8080/image/simpleFold.png" width="20" height="20" align="left" vspace="5" hspace="5"/>
                    <div>
                        <span style="font-size: 20px">
                            ${folder.name}
                        </span>
                    </div>
                </center>
            </div>
        <div>

    </body>
</html>