<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up Form by Colorlib</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="css/signup.css">
</head>
<body>

    <div class="main-layout">

        <!-- Sign up form -->
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form main-form">
                        <h2 class="form-title">Sign Up</h2>
                        <form method="POST" class="register-form" id="register-form" action="signup">
                            <div class="form-group">
                                <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="name" id="name" placeholder="Your Name" required/>
                            </div>
                            <div class="form-group">
                                <label for="surname"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="surname" id="surname" placeholder="Your Surname" required/>
                            </div>
                            <div class="form-group">
                                <label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="username" id="username" placeholder="Your Username" required/>
                            </div>
                            <div class="form-group">
                                <label for="email"><i class="zmdi zmdi-email"></i></label>
                                <input type="email" name="email" id="email" placeholder="Your Email" required/>
                            </div>
                            <div class="form-group">
                                <label for="psw"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" name="psw" id="psw" placeholder="Password" required/>
                            </div>
                            <div class="form-group">
                                <label for="nationality"><i class="zmdi zmdi-lock-outline"></i></label>
                                <input type="text" name="nationality" id="nationality" placeholder="Insert your Nationality"/>
                            </div>
                            <div class="form-group">
                                <label for="languages"><i class="zmdi zmdi-lock-outline"></i></label>
                                <input type="text" name="languages" id="languages" placeholder="Which lenguage do you speak separated by a comma"/>
                            </div>
                            
                            <div class="form-group">
                                <button type="submit" name="signup" id="signup" class="form-submit">Register</button>
                            </div>
                        </form>
                    </div>
                    <div class="signup-image main-form">
                        <figure><img src="images/signup-image.jpg" alt="sing up image"></figure>
                        <a href="signin.html" class="signup-image-link">I am already member</a>
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