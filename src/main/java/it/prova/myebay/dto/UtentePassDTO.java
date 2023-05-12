package it.prova.myebay.dto;

public class UtentePassDTO {

	private Long id;

	private String password;

	private String confermaPassword;
	
	private String nuovaPassword;


	public UtentePassDTO() {
	}


	

	public UtentePassDTO(Long id,
			String password,
			String confermaPassword, String nuovaPassword) {
		super();
		this.id = id;
		this.password = password;
		this.confermaPassword = confermaPassword;
		this.nuovaPassword = nuovaPassword;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




	public String getConfermaPassword() {
		return confermaPassword;
	}




	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}




	public String getNuovaPassword() {
		return nuovaPassword;
	}




	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}

	

}
