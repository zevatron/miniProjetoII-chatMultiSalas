package br.edu.ifpb.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/{sala}/{usuario}")
public class ServerWS {
	
//	private static List<Session> salas1 = Collections.
//			synchronizedList(new ArrayList<>());
	
	private static Map<String, Map<String,Session>> salas = Collections.
			synchronizedMap(new HashMap<>());
	
	@OnOpen
	public void conectar(@PathParam("sala") String sala, 
			@PathParam("usuario") String usuario , Session s) {
		
		if (salas.get(sala) == null ) {
			
			salas.put(sala, new HashMap<>() );
			System.out.println("Nova sala criada");
		}
		
		if(salas.get(sala).get(usuario) == null) {
			salas.get(sala).put(usuario, s);
			System.out.println("Entrou na sala");
		}else {
			try {
				s.getBasicRemote().sendText("Já existe um usuário conectado com o nome: \""+usuario+"\". Tente novamente!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
