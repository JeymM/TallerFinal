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

import Entidades.Transacciones;
import SERVICES.Service;
import SERVICES.ServiceUser;

public class ServletTransacciones extends HttpServlet {

	
	private Service serviciotransaccion;
	private ObjectMapper mapper;

	 public ServletTransacciones() {
		
		serviciotransaccion = new ServiceUser();
		mapper = new ObjectMapper();
	} 
	
	
	
	   @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String path = request.getPathInfo();
	        if (path == null) {
	            List<Transacciones> transaccion = (List<Transacciones>) serviciotransaccion.Lista();
	            String json = mapper.writeValueAsString(transaccion);
	            response.setContentType("application/json");
	            response.getWriter().println(json);
	        } else {
	            switch (path) {
	                case "/buscar":
	                    String id = request.getParameter("id");
	                    try {
	                        Transacciones  transaccion= (Transacciones) serviciotransaccion.Buscar(id);
	                        String json = mapper.writeValueAsString(transaccion);
	                        response.setContentType("application/json");
	                        response.getWriter().println(json);
	                    } catch (Exception e) {
	                        response.setStatus(404);
	                        Map<String, String> error = new HashMap<>();
	                        error.put("error", e.getMessage());
	                        String json = mapper.writeValueAsString(error);
	                        response.setContentType("application/json");
	                        response.getWriter().println(json);
	                    }
	                    break;
	                default:
	                    response.setStatus(404);
	                    Map<String, String> error = new HashMap<>();
	                    error.put("error", "No se ha realizado transacciones");
	                    String json = mapper.writeValueAsString(error);
	                    response.setContentType("application/json");
	                    response.getWriter().println(json);
	                    break;
	            }

	        }

	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String content = request.getContentType();

	        if (content != null && content.equals("application/json")) {
	            Map<String, Object> transaccionMap = mapper.readValue(request.getInputStream(), HashMap.class);
	            try {
	               serviciotransaccion.Guardar(transaccionMap);
	                response.setStatus(HttpServletResponse.SC_CREATED);
	                Map<String, String> respuesta = new HashMap<>();
	                respuesta.put("mensaje", "Transaccion exitosa");
	                String json = mapper.writeValueAsString(respuesta);
	                response.setContentType("application/json");
	                response.getWriter().println(json);

	            } catch (Exception e) {
	                response.setStatus(HttpServletResponse.SC_CONFLICT);
	                Map<String, String> error = new HashMap<>();
	                error.put("error", e.getMessage());
	                String json = mapper.writeValueAsString(error);
	                response.setContentType("application/json");
	                response.getWriter().println(json);
	            }

	        } else {
	            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
	            Map<String, String> error = new HashMap<>();
	            error.put("error", "El contenido debe ser JSON");
	            String json = mapper.writeValueAsString(error);
	            response.setContentType("application/json");
	            response.getWriter().println(json);
	        }
	    }

	    @Override
	    protected void doPut(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String content = request.getContentType();
	        if(content == "application/json") {
	            Map <String, Object> transaccionMap = mapper.readValue(request.getInputStream(), HashMap.class);

	            try {
	                serviciotransaccion.actualizar(content, content);
	                response.setStatus(HttpServletResponse.SC_OK);
	                Map<String, String> respuesta = new HashMap<>();
	                respuesta.put("mensaje", "Persona actualizada con exito");
	                String json = mapper.writeValueAsString(respuesta);
	                response.setContentType("application/json");
	                response.getWriter().println(json);
	                
	            } catch (Exception e) {
	                response.setStatus(HttpServletResponse.SC_CONFLICT);
	                Map<String, String> error = new HashMap<>();
	                error.put("error", e.getMessage());
	                String json = mapper.writeValueAsString(error);
	                response.setContentType("application/json");
	                response.getWriter().println(json);
	            }
	        
	        } else {
	            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
	            Map<String, String> error = new HashMap<>();
	            error.put("error", "El contenido debe ser JSON");
	            String json = mapper.writeValueAsString(error);
	            response.setContentType("application/json");
	            response.getWriter().println(json);
	        }
	    }

	    @Override
	    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String identificador = request.getParameter("identificador");
	        try {
	            serviciotransaccion.eliminar(identificador);

	            response.setStatus(HttpServletResponse.SC_OK);
	            Map<String, String> respuesta = new HashMap<>();
	            respuesta.put("mensaje", "Transaccion eliminada con exito");
	            String json = mapper.writeValueAsString(respuesta);
	            response.setContentType("application/json");
	            response.getWriter().println(json);
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_CONFLICT);
	            Map<String, String> error = new HashMap<>();
	            error.put("error", e.getMessage());
	            String json = mapper.writeValueAsString(error);
	            response.setContentType("application/json");
	            response.getWriter().println(json);
	        }
	    }
}
