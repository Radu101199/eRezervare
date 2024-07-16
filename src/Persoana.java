public class Persoana {

	private int personaID;
	private String nume;
	private String serieNr;
	private String cnp;
	private String stradaNr;
	private String telefon;

	public Persoana(String nume, String serieNr, String cnp, String stradaNr, String telefon) {
		this.nume = nume;
		this.serieNr = serieNr;
		this.cnp = cnp;
		this.stradaNr = stradaNr;
		this.telefon = telefon;
	}

	public int getPersonaID() {
		return personaID;
	}

	public void setPersonaID(int personaID) {
		this.personaID = personaID;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getSerieNr() {
		return serieNr;
	}

	public void setSerieNr(String serieNr) {
		this.serieNr = serieNr;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getStradaNr() {
		return stradaNr;
	}

	public void setStradaNr(String stradaNr) {
		this.stradaNr = stradaNr;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
