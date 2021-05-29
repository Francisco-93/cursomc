package com.franciscoaguiar.cursomc.domain.enums;

public enum Perfil {

	ADMIN (1, "ROLE_ADMIN"),
	CLIENTE (2, "ROLE_CLIENTE");


	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		for(Perfil e : Perfil.values()) {
			if(e.getCod() == cod) {
				return e;
			}
		}
		return null;
	}

}