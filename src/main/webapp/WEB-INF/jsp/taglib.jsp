<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置一个项目路径的变量,配置一个全局的ctx变量,这样所有的jsp页面中,都可以引用该变量了.  -->
<!-- 但是该文件必须在web.xml文件中配置一下. -->
<!-- 此处其实就是利用了OGNL表达式进行了存储. -->
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

