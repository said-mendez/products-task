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
    </body>
</html>