<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasks</title>
    </head>
    <body>
        <h4>Tasks</h4>
        ${message}
        <table>
        <c:forEach var="task" items="${tasklist}">
            <tr>
                <td>id: ${task.id}</td>
                <td>name: ${task.name}</td>
                <td>
                    <form method="delete" action="deletetask${task.id}">
                        <input type="submit" value="удалить"/>
                    </form>    
                </td>
                <td>
                    <form:form modelAttribute="edittask"  method="get" action="edittaskform">
                        <form:input type="hidden" path="id" value = "${task.id}" />
                        <form:input type="hidden" path="name" value = "${task.name}" />
                        <input type="submit" value="изменить"/>
                    </form:form>    
                </td>
            </tr>    
        </c:forEach>
        </table>    
        <br>
        <a href="addtaskform">Добавить задачу</a>
    </body>
</html>
