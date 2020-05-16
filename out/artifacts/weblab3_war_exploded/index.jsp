<%--
  Created by IntelliJ IDEA.
  User: Julia
  Date: 13.05.2020
  Time: 0:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Online-shop</title>
  <link rel="stylesheet" type="text/css" href="common.css"/>
</head>
<body>

<form action="uservalid" method="POST">
  <div class="wrap">

    <h1>Online-shop</h1>
    <p>${err_msg}</p>

    <label class="lbl1" for="login">Login:</label>
    <input type="login" id="login" name="login" class="login" required
           style="${req_check}"/>
    <br><br>
    <label class="lbl2" for="password">Password:</label>
    <input type="password" id="password" name="password" class="password" required
          style="${req_check}"/>
    <br><br>
    <input type="submit" value="Submit" class="btn"/>

  </div>
</form>

</body>
</html>