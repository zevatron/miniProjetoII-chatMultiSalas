package br.edu.ifpb.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/{sala}/{usuario}")
public class ServerWS {

	// private static List<Session> salas1 = Collections.
	// synchronizedList(new ArrayList<>());

	private static Map<String, Map<String, Session>> salas = Collections
			.synchronizedMap(new HashMap<String, Map<String, Session>>());

	@OnOpen
	public void conectar(@PathParam("sala") String sala, @PathParam("usuario") String usuario, Session s) throws IOException {

		if (salas.get(sala) == null) {

			salas.put(sala, new HashMap<String, Session>());
			System.out.println("Nova sala criada");
		}

		if (salas.get(sala).get(usuario) == null) {
			salas.get(sala).put(usuario, s);
			System.out.println("Entrou na sala");
		} else {
			s.getBasicRemote().sendText("Já existe um usuário conectado com o nome: \"" + usuario + "\"");
			usuario = usuario + new Date().getTime();
			s.getBasicRemote().sendText("Seu nome de usuário oi alterado para: \"" + usuario
					+ "\". Você poderá alterar seu nome a qualquer momento.");
			// s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Já existe um
			// usuário conectado com o mesmo nome:"));
			salas.get(sala).put(usuario, s);
		}

	}

	@OnClose
	public void desconectar(@PathParam("sala") String sala, @PathParam("usuario") String usuario, Session s)
			throws IOException {

		s.getBasicRemote().sendText(usuario + "saiu da sala...");

		if (salas.get(sala).remove(usuario, s)) {
			System.out.println(usuario + " saiu da sala: " + sala);
		}

		if (salas.get(sala).values().isEmpty()) {
			salas.remove(sala);
		}
	}

}
