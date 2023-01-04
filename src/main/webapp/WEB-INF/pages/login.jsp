<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>

    <!-- JS -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main.js"></script>
    <!-- Font Icon -->
    <link rel="stylesheet" href="Webcontent/fonts/material-icon/css/material-design-iconic-font.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- Main css -->
    <link rel="stylesheet" href="WebContent/css/signup.css">

</head>

<body class="log-bkg col-12">
<div class="signup ">
        <div class="container" >
            <div class="row justify-content-end text-right pr-5">
                <h2 class="signup-image-link mt-4 mr-3"> Go back to homepage!</h2>
                <a href="home" >
                <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" fill="black" viewBox="0 0 16 16">
                    <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L2 8.207V13.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V8.207l.646.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5ZM13 7.207V13.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V7.207l5-5 5 5Z"/>

                </svg>
            </a>


            </div>
            <div class="signup-content col-12">
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
                <div class="col-6">


                    <div class="signup-image">
                            <figure><img src="WebContent/images/signin.jpg" alt="sing up image"></figure>
                        <a href="signup" class="signup-image-link">I am not registered! <br> Register now!</a>
                    </div>
                </div>

            </div>
        </div>
</div>



</body>
</html>




