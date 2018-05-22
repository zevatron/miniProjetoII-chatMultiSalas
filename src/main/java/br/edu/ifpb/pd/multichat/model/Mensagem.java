package br.edu.ifpb.pd.multichat.model;

import java.util.Date;

public class Mensagem {

	private String remetente, destinatario, conteudo;
	private Boolean reservada;

	public Mensagem() {
		// TODO Auto-generated constructor stub
	}

	public Mensagem(String remetente, String destinatario, String conteudo) {
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.conteudo = conteudo;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Boolean getReservada() {
		return reservada;
	}

	public void setReservada(Boolean reservada) {
		this.reservada = reservada;
	}

	@Override
	public String toString() {
		return remetente + " " + new Date() + ": " +  conteudo ;
	}
	
	

}
