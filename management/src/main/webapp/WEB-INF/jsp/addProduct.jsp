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
  <section>
    <form:form id="add-product" method="POST" action="" modelAttribute="">
        <form:label path="name">Product Name:</form:label>
        <form:input type="text" path="name" required="required" />
        <form:label path="description">Product Description:</form:label>
        <form:input type="text" path="description" />
        <form:label path="price">Product Price:</form:label>
        <form:input type="number" min="0.01" step="0.01" path="description" required="required" />
    </form:form>
  </section>
</body>
</html>