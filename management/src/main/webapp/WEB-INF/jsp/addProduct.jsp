<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Products</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
              <jsp:param name="header" value="Add Product" />
        </jsp:include>
        <section class="section mt-5">
            <c:if test="${addProductSuccess}">
                <c:if test="${responseStatus.success}">
                    <div class="notification is-success">
                      <button class="delete"></button>
                      <strong><c:out value="${responseStatus.message}"/></strong>
                    </div>
                </c:if>
                <c:if test="${not responseStatus.success}">
                    <div class="notification is-danger">
                      <button class="delete"></button>
                      <c:out value="${responseStatus.message}"/>
                      <strong><c:out value="${responseStatus.exception}"/></strong>
                    </div>
                </c:if>
            </c:if>
        </section>
        <main class="container">
            <section class="section box mt-2">
                <form:form id="add-product" method="post" action="/products/addProduct" modelAttribute="product">
                    <div class="field">
                        <form:label path="name" class="label">Product Name:</form:label>
                        <div class="control">
                            <form:input type="text" path="name" class="input" maxlength="255" placeholder="Product Name" required="required" />
                        </div>
                    </div>
                    <div class="field">
                        <form:label path="description" class="label">Product Description:</form:label>
                        <div class="control">
                            <form:input type="text" path="description" class="input" maxlength="500" placeholder="Product Description" />
                        </div>
                    </div>
                    <div class="field">
                        <form:label path="price" class="label">Product Price:</form:label>
                        <div class="control">
                            <form:input type="number" min="0.01" step="0.01" path="price" required="required" />
                        </div>
                    </div>

                    <div class="control mt-2">
                        <button class="button is-link">Submit</button>
                    </div>
                </form:form>
            </section>
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