<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>

<body>
    <form name="loginForm" method="post" action="login">
        Username: <input type="text" name="username"/> <br/>
        Password: <input type="password" name="password"/> <br/>
        <input type="submit" value="Login" />
    </form>

    <%
        String message = (String) request.getAttribute("errorMessage");

        if(message != null && !message.equals("")){
            %>
            Error:
            <%= message %>
            <%
        }
    %>


</body>