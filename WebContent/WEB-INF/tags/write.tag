<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${mid==null}">
	<input type="text" name="msg" disabled="disabled" value="로그인후에 글 등록이 가능합니다!" size="50">
</c:if>
<c:if test="${mid!=null}">
	<input type="text" name="msg" placeholder="글 내용을 작성하세요." size="50">
	<input type="submit" value="글 등록">
</c:if>