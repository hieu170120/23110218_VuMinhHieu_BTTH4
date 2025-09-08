<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
    <c:when test="${sessionScope.account == null}">
        <div class="col-sm-6">
            <ul class="list-inline right-topbar pull-right">
                <li>
                    <a href="/BTTH04/views/login.jsp">Đăng nhập</a> | 
                    <a href="/BTTH04/views/register.jsp">Đăng ký</a>
                </li>
                <li><i class="search fa fa-search search-button"></i></li>
            </ul>
        </div>
    </c:when>
    <c:otherwise>
        <div class="col-sm-6">
            <ul class="list-inline right-topbar pull-right">
                <li>
                    <a href="/member/myaccount">
                        ${sessionScope.account.fullName}
                    </a> | 
                    <a href="/logout">Đăng Xuất</a>
                </li>
                <li><i class="search fa fa-search search-button"></i></li>
            </ul>
        </div>
    </c:otherwise>
</c:choose>
