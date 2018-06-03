package br.edu.ifpb.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
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
		enviarParaTodos(mensagem(usuario,"entrou na sala"), sala);

	}

	@OnClose
	public void desconectar(@PathParam("sala") String sala, @PathParam("usuario") String usuario, Session s)
			throws IOException {

//		s.getBasicRemote().sendText(usuario + "saiu da sala...");

		if (salas.get(sala).containsValue(s)) {	
			System.out.println(usuario + " saiu da sala: " + sala);
			enviarParaTodosMenosSession(mensagem(usuario, "Saiu da sala"), sala,s);
			salas.get(sala).remove(usuario, s);
		}

		if (salas.get(sala).values().isEmpty()) {
			salas.remove(sala);
			System.out.println("Sala: " +sala+ " foi removida");
		}
	}
	
	@OnMessage
	public void onMessage(String mensagem, @PathParam("sala") String sala, @PathParam("usuario") String usuario) throws IOException {
		String send = mensagem.split(" ")[0];
		Boolean reservado = Arrays.asList( mensagem.split(" ") ).contains("-u");
		
		if(send.equals("send")) {
			
			if(reservado) {
				return;
			}else {
				String m = mensagem.substring(5);
				enviarParaTodos(mensagem(usuario, m), sala);
			}
		}
		
	}

	private void enviarParaTodos(String mensagem, String sala) throws IOException {
		for( Session s : salas.get(sala).values()) {
			s.getBasicRemote().sendText(mensagem);
		}
	}
	
	private void enviarParaTodosMenosSession(String mensagem, String sala, Session session) throws IOException {
		for( Session s : salas.get(sala).values()) {
			if(!s.equals(session)) {
				s.getBasicRemote().sendText(mensagem);				
			}
		}
	}
	
	private String mensagem(String usuario, String m) {
		return usuario + " " + getHora() + m;
	}
	
	private String getHora() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss : ");
		Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
		String horaFormatada = sdf.format(hora);
		return  horaFormatada;
	}

}
