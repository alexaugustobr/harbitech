<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Harbitech</title>
</head>
<body>
<center><h1>Lista de categorias</h1> <br/></center>
<table border=1 frame=void rules=rows>

<tr align =center>
    <th scope="row">Nome</th>
    <th scope="row">C�digo da url</th>
    <th scope="row">Descri��o &ensp;</th>
    <th scope="row">�cone</th>
    <th scope="row">Cor</th>
    </tr>

    <tr>
        <c:forEach items="${categories}" var="category">
    <tr>
    <td scope="row">${category.name} <td/>
    <td scope="row">${category.codeUrl}<td/>
    <td scope="row">${category.description}<td/>
    <td scope="row">${category.iconPath}<td/>
    <td scope="row">${category.htmlHexColorCode}<td/>
    </tr>
        </c:forEach>
    </td>

    <br>
    <br>

</tr>
</table>
</body>
</html>