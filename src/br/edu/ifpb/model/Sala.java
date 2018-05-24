package br.edu.ifpb.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {

	private String sala;
	private List<String> usuarios;

	public Sala() {
	}

	public Sala(String sala) {
		this.sala = sala;
		this.usuarios = new ArrayList<>();
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

}
