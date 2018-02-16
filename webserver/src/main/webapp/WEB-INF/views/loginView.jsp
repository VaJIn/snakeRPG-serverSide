<%--
  Created by IntelliJ IDEA.
  User: pviolette
  Date: 15/02/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <form class="p-2" action="<c:url value="/login/" />" id="<c:out value="${id['form']}"/>" method="POST">
        <div class="form-group">
            <label for="<c:out value="${id['accountName']}"/>">Account Name</label>
            <input id="<c:out value="${id['accountName']}"/>" name="${id['accountName']}" class="form-control"
                   type="text" required/>
            <span class="invalid-feedback"><c:out value="${error['accountName']}"/>}</span>
        </div>
        <div class="form-group">
            <label for="<c:out value="${id['password']}"/>">Password</label>
            <input id="<c:out value="${id['password']}"/>" name="${id['password']}" class="form-control" type="password"
                   required/>
            <span class="invalid-feedback"><c:out value="${error['password']}"/></span>
        </div>
        <input type="submit" class="btn btn-primary" value="Log in"/>
        <small>
            Not registered yet ? <a href="<c:url value="/register/" />">Register here !</a>
        </small>
        <span class="invalid-feedback"><c:out value="${error['loginError']}"/></span>
    </form>
</div>