<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Clientes</title>
</head>
<body>
    <h2>Clientes Registrados</h2>
    <table border='1'>
        <tr><th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th></tr>
        <%
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {
                String url = "jdbc:postgresql://localhost:5432/sanducheriadb";
                String usuario = "saulito";
                String password = "25162516";
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, usuario, password);
                statement = connection.createStatement();
                rs = statement.executeQuery("SELECT * FROM clientes");

                while (rs.next()) {
        %>
                    <tr>
                        <td><%= rs.getInt("id_clientes") %></td>
                        <td><%= rs.getString("nombre") %></td>
                        <td><%= rs.getString("correo") %></td>
                        <td><%= rs.getString("telefono") %></td>
                    </tr>
        <%
                }
            } catch (SQLException | ClassNotFoundException ex) {
                out.println("<p>Error en la base de datos: " + ex.getMessage() + "</p>");
            } finally {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            }
        %>
    </table>
    <br>
    <a href="index.html">Volver</a>
</body>
</html>
