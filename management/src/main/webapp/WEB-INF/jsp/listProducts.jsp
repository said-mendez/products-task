<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Products</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    </head>
    <style>
        th {
            background-color: #b794f6f4;
        }
    </style>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="header" value="Products List" />
        </jsp:include>

        <main class="container mt-5 mb-5">
            <c:if test="${not products.responseStatus.status.success && products.responseStatus.status.message != null}">
                <div class="notification is-danger mt-5">
                  <button class="delete"></button>
                  <c:out value="${products.responseStatus.status.message}"/>
                  <strong><c:out value="${products.responseStatus.status.exception}"/></strong>
                </div>
            </c:if>
            <div class="table-container">
              <table class="table is-bordered is-striped is-narrow is-hoverable is-fullwidth">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${products.responseStatus.status.success}">
                        <c:forEach items="${products.data}" var="product">
                            <tr>
                                <td>${product.id}</td>
                                <td>${product.name}</td>
                                <td>${product.description}</td>
                                <td>${product.price}</td>
                                <td>${product.createdAt}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
              </table>
            </div>
        </main>

        <script type="text/javascript">
            document.addEventListener('DOMContentLoaded', () => {
              (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
                const $notification = $delete.parentNode;

                $delete.addEventListener('click', () => {
                  $notification.parentNode.removeChild($notification);
                });
              });
            });
        </script>
    </body>
</html>