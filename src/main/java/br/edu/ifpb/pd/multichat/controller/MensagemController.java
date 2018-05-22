package br.edu.ifpb.pd.multichat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.edu.ifpb.pd.multichat.model.Mensagem;

@Controller
public class MensagemController {
	
	@MessageMapping("/{sala}/{usuario}")
	@SendTo("/{sala}")
	public Mensagem resposta (Mensagem m) {
		return m;
	}

}
