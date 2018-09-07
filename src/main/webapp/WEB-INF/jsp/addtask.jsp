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
        <h4>Task</h4>
        <c:choose>
            <c:when test="${task.id != 0}">
                <form:form modelAttribute="task" method="post" action="edittask${task.id}">
                    <p><b>Название задачи:</b><br>
                        <form:input type="hidden" path="id" id="id" value="${task.id}" />    
                        <form:input type="text" path="name" size="30" value="${task.name}" />
                    </p>
                    <input type="submit" value="Изменить"/>
                </form:form>
                <br>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="task" method="post" action="addtask">
                    <p><b>Название задачи:</b><br>
                        <form:input type="hidden" path="id" id="id" value="${task.id}"/>    
                        <form:input type="text" path="name" size="30" value="${task.name}"/>
                    </p>
                    <input type="submit" value="Добавить"/>
                </form:form>
                <br>
            </c:otherwise>
        </c:choose>
        ${err_message}
        <br>
        <a href="task">Вернуться к списку задач</a> 
        <br>
    </body>
</html>
