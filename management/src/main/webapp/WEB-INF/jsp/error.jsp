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

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="header" value="Error" />
        </jsp:include>

        <main class="container mt-5 mb-5">
            <article class="message is-danger">
              <div class="message-header">
                <p><c:out value="${responseStatus.exception}"/></p>
              </div>
              <div class="message-body">
                <c:out value="${responseStatus.message}"/>
              </div>
            </article>
        </main>
    </body>
</html>