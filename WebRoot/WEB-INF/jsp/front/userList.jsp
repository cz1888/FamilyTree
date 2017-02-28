<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/front/taglibs.jspf" %>
<c:forEach var="user"   items="${u.records}" >
${user.username}-----${user.pwd}
</c:forEach>
  
  
  