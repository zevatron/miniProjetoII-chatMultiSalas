package br.edu.ifpb.model;

public class Usuario {
	
	private String nome;
	private String sala;
	
	
	
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Usuario(String nome, String sala) {
		super();
		this.nome = nome;
		this.sala = sala;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSala() {
		return sala;
	}
	public void setSala(String sala) {
		this.sala = sala;
	}
	
	

}
