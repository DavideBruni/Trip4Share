<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="Webcontent/fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="WebContent/css/signup.css">

</head>

<body>

<div class="main-layout">

    <!-- Sign up form -->
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form main-form">
                    <h2 class="form-title">Sign in</h2>
                    <form method="POST" class="register-form" id="register-form">
                        <div class="form-group">
                            <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="username" id="name" placeholder="Username"/>
                        </div>
                        <div class="form-group">
                            <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="pass" placeholder="Password"/>
                        </div>

                        <%
                            String message = (String) request.getAttribute("errorMessage");

                            if(message != null && !message.equals("")){
                        %>
                        Error:
                        <%= message %>
                        <%
                            }
                        %>


                        <div class="form-group">
                            <button type="submit" name="login" id="login" class="form-submit">Login</button>
                        </div>
                    </form>
                </div>
                <div class="signup-image main-form">
                    <figure><img src="images/signup-image.jpg" alt="sing up image"></figure>
                    <a href="signup" class="signup-image-link">Join us now!</a>
                </div>
            </div>
        </div>
    </section>

</div>

<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>




