package BD;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Entidades.Cuenta;

public class Mapeo {
	public static String Json(Object objecto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if(objecto == null){
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "usuario no encontrado");
            return mapper.writeValueAsString(respuesta);
        }

        return mapper.writeValueAsString(objecto);
    }

    public static String Salida(String mensaje) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        return mapper.writeValueAsString(respuesta);
    }

    public static String Error(String mensaje) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", mensaje);
        return mapper.writeValueAsString(respuesta);
    }

	public static char[] ObtenerJson(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return null;
	}
}
