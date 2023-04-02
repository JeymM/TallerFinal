package Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InvalidAttributeValueException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import BD.Mapeo;
import Entidades.Cuenta;
import SERVICES.Service;
import SERVICES.ServicesAccount;

public class ControllerCuenta extends HttpServlet {

	private Service servicio;
	private ObjectMapper mapper;

	public ControllerCuenta() {
		servicio = new ServicesAccount();
		mapper = new ObjectMapper();
	}

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		
			

		
			switch (path) {
			case "/buscar":
				String numeroCuenta = request.getParameter("numeroCuenta");
				try {
					Cuenta cuenta = (Cuenta) servicio.Buscar(numeroCuenta);
					String json = mapper.writeValueAsString(cuenta);
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
				error.put("error", "No se encontro la cuenta");
				String json = mapper.writeValueAsString(error);
				response.setContentType("application/json");
				response.getWriter().println(json);
				break;
			}

		}

	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String content = request.getContentType();

		if (content != null && content.equals("application/json")) {
			Map<String, Object> cuentaMap = mapper.readValue(request.getInputStream(), HashMap.class);
			try {
				servicio.Guardar(cuentaMap);
				response.setStatus(HttpServletResponse.SC_CREATED);
				Map<String, String> respuesta = new HashMap<>();
				respuesta.put("mensaje", "Cuenta guardada con exito");
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
		if (request.getContentType().equals("application/json")) {
			try {
				switch (request.getPathInfo()) {
				case "/eliminar":
					String numeroCuenta = request.getParameter("numeroCuenta");
					if (numeroCuenta == null || numeroCuenta.equals("")) {
						throw new InvalidAttributeValueException("Numero de cuenta obligatorio");
					}

					String Eliminacion = servicio.eliminar(numeroCuenta);

					response.setStatus(HttpServletResponse.SC_CREATED);
					response.setContentType("application/json");
					response.getWriter().println(Mapeo.Salida(Eliminacion));
					break;
				default:
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontro la cuenta");
					break;
				}
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json");
				response.getWriter().println(Mapeo.Error(e.getMessage()));
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			response.setContentType("application/json");
			response.getWriter().println(Mapeo.Error("El contenido debe ser JSON"));
		}
	}
}
