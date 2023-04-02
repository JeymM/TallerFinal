package Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import SERVICES.Service;
import SERVICES.ServiceUser;
import SERVICES.ServicesAccount;

public class ServletUsuario<Usuario> extends HttpServlet{
	
	private Service servicio;
	private ObjectMapper mapper;

	public ServletUsuario() {
		servicio = new ServiceUser();
		mapper = new ObjectMapper();
	}
	
	  @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String path = req.getPathInfo();
	       
	            switch (path){
	            
	                case "/listarTodos":
	                    try {
	                        String id = req.getParameter("id");
	                        List<Usuario> usuario = (List<Usuario>) servicio.Lista();
	                        String json = mapper.writeValueAsString(usuario);
	                        resp.setContentType("application/json");
	                        resp.getWriter().println(json);
	                    } catch (Exception e){
	                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                        Map<String, String> error = new HashMap<>();
	                        error.put("error", e.getMessage());
	                        String json = mapper.writeValueAsString(error);
	                        resp.setContentType("application/json");
	                        resp.getWriter().println(json);
	                    }
	                    break;
	              
	                case "/buscaporid":
	                    try {
	                        String id = req.getParameter("id");
	                        Usuario usuario= (Usuario) servicio.Buscar(id);
	                        String json = mapper.writeValueAsString(usuario);
	                        resp.setContentType("application/json");
	                        resp.getWriter().println(json);
	                    } catch (Exception e){
	                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                        Map<String, String> error = new HashMap<>();
	                        error.put("Error", e.getMessage());
	                        String json = mapper.writeValueAsString(error);
	                        resp.setContentType("application/json");
	                        resp.getWriter().println(json);
	                    }
	                    break;
	                default:
	                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                    Map<String, String> error = new HashMap<>();
	                    error.put("error", "No se encontro la cuenta");
	                    String json = mapper.writeValueAsString(error);
	                    resp.setContentType("application/json");
	                    resp.getWriter().println(json);
	            }
	        }
	    

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String content = req.getContentType();
	        if (content != null && content.equals("application/json")){
	            Map<String, Object> UserMap = mapper.readValue(req.getInputStream(), HashMap.class);
	            try {
	                servicio.Guardar(UserMap);
	                resp.setStatus(HttpServletResponse.SC_CREATED);
	                Map<String, String> respuesta = new HashMap<>();
	                respuesta.put("mensaje", "Nuevo usuario creada exitosamente");
	                String json = mapper.writeValueAsString(respuesta);
	                resp.setContentType("application/json");
	                resp.getWriter().println(json);
	            } catch (Exception e) {
	                resp.setStatus(HttpServletResponse.SC_CONFLICT);
	                Map<String, String> error = new HashMap<>();
	                error.put("error", e.getMessage());
	                String json = mapper.writeValueAsString(error);
	                resp.setContentType("application/json");
	                resp.getWriter().println(json);
	            }

	        } else {
	            resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
	            Map<String, String> error = new HashMap<>();
	            error.put("error", "El contenido debe ser JSON");
	            String json = mapper.writeValueAsString(error);
	            resp.setContentType("application/json");
	            resp.getWriter().println(json);
	        }
	    }

	  
	    @Override
	    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String id = req.getParameter("id");
	        try{
	            servicio.eliminar(id);

	            resp.setStatus(HttpServletResponse.SC_OK);
	            Map<String, String> respuesta = new HashMap<>();
	            respuesta.put("mensaje", "User eliminada con exito");
	            String json = mapper.writeValueAsString(respuesta);
	            resp.setContentType("application/json");
	            resp.getWriter().println(json);
	        } catch (Exception e){
	            resp.setStatus(HttpServletResponse.SC_CONFLICT);
	            Map<String, String> error = new HashMap<>();
	            error.put("error", e.getMessage());
	            String json = mapper.writeValueAsString(error);
	            resp.setContentType("application/json");
	            resp.getWriter().println(json);
	        }
	    }

}
