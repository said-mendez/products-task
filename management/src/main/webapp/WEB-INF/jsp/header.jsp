<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
</head>

<%String header = request.getParameter("header");%>

<nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="/products">
      Products Task
    </a>

    <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>

  <div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-start">
      <a class="navbar-item" href="/products">
        List Products
      </a>

      <a class="navbar-item" href="/products/addProduct">
        Add Products
      </a>
    </div>
  </div>
</nav>

<section class="hero is-link">
  <div class="hero-body">
    <p class="title">
      My Products App
    </p>
    <p class="subtitle">
      <%= header %>
    </p>
  </div>
</section>