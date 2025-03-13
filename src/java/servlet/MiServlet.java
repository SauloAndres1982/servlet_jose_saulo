package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MiServlet")
public class MiServlet extends HttpServlet {
    
    private final String url = "jdbc:postgresql://localhost:5432/sanducheriadb";
    private final String usuario = "saulito";
    private final String password = "25162516";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, usuario, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM clientes");

            out.println("<html><head><title>Lista de Clientes</title></head><body>");
            out.println("<h2>Clientes Registrados</h2>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th></tr>");
            
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id_clientes") + "</td><td>" +
                            rs.getString("nombre") + "</td><td>" +
                            rs.getString("correo") + "</td><td>" +
                            rs.getString("telefono") + "</td></tr>");
            }

            out.println("</table><br>");
            out.println("<a href='index.html'>Volver</a>");
            out.println("</body></html>");

            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MiServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, usuario, password);
            
            String sql = "INSERT INTO clientes(nombre, correo, telefono) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, telefono);
            ps.executeUpdate();
            
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MiServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("MiServlet"); // Redirige a la lista de clientes
    }
}
