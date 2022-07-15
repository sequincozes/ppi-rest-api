package exemplo_simplificado;

import com.google.gson.Gson;
import exemplo_simplificado.entity.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "usuarioEndPoint", value = "/usuario")
public class UsuarioEndPoint extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello Usuario!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Aqui a API buscará o dado em um banco de dados qualquer (vamos inicializar nós mesmos para simular isso)
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario u = new Usuario(id, "Silvio", 17+id);

        // Usando GSON para retornar um usuario no formato JSON
        String json = new Gson().toJson(u);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    public void destroy() {
    }
}