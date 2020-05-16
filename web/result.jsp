<%--
  Created by IntelliJ IDEA.
  User: Julia
  Date: 13.05.2020
  Time: 4:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Customer" %>
<%@ page import="classes.Order" %>
<%@ page import="classes.Item" %>
<%
    Customer customer = (Customer)session.getAttribute("user");
%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="common.css">
</head>
<body>
<header>
            <h1>Online-Shop</h1>
            <h2>Welcome, <%=customer.getCustomerName()%>! Here is a list of all your orders.</h2>
</header>
<div class="container">
    <table>
        <tr>
            <th>OrderID</th>
            <th>itemName</th>
            <th>Quantity</th>
            <th>Cost</th>
        </tr>
        <%
            for (int i = 0; i < customer.getLength(); i++) {
                for (int j = 0; j < customer.getOrderList().get(i).getLength(); j++) {
                    Item tmp = customer.getOrderList().get(i).getItemFromOrder(j);
        %>

        <tr>
            <td><%=customer.getOrderList().get(i).getOrder_id()%></td>
            <td><%=tmp.getName()%></td>
            <td><%=tmp.getAmount()%></td>
            <td><%=tmp.getCost()%></td>
        </tr>

        <%
                }
            }
        %>
    </table>
</div>

<div class = "links">
    <a href="index">Log out</a>
    <a href="result.xml">Download result.xml</a>
</div>

</body>
</html>
