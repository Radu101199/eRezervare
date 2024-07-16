public class Angajat {
    private int angajatId;
    private String username;
    private String CNP;
    private String parola;
    private int functie;

    public Angajat(String username, String CNP, String parola, int functie) {
        this.username = username;
        this.CNP = CNP;
        this.parola = parola;
        this.functie = functie;
    }

    public int getAngajatId() {
        return angajatId;
    }

    public String getUsername() {
        return username;
    }

    public String getCNP() {
        return CNP;
    }

	public String getParola() {
        return parola;
    }

    public int getFunctie() {
        return functie;
    }
    
    public void setAngajatId(int angajatId) {
		this.angajatId = angajatId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCNP(String cNP) {
		CNP = cNP;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public void setFunctie(int functie) {
		this.functie = functie;
	}
}
