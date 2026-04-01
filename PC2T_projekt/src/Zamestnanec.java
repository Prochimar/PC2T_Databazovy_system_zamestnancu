import java.util.Map;

public class Zamestnanec {
	private int ID;
	private int rok;
	private String jmeno;
	private String prijmeni;
	private String typ;
	private Map<Integer,String> Spolupracovnici; //pokud to chapu dobre, zamestnanec si eviduje ID a uroven spoluprace, ne celeho zamestnance
	
	public Zamestnanec(int ID, String jmeno, String prijmeni, int rok, String typ) {
		this.ID = ID;
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.rok = rok;
		this.typ = typ; //je dobre popremyslet, jeslti budeme zadavat typy zamnestnancu jako str nebo ne, prozatim delam jen tu databazi ale
	}
	
	public int getID() {
		return ID;
	}
	
	public int getRok() {
		return rok;
	}
	
	public String getJmeno() {
		return jmeno;
	}
	
	public String getPrijmeni() {
		return prijmeni;
	}
	
	public String getTyp() {
		return typ;
	}
	
	public Map<Integer,String> getSpolupracovnici() {
		return Spolupracovnici;
	}
	
	public void setSpolupracovnici(int ID, String ur_spol) {
		Spolupracovnici.put(ID, ur_spol);
	}
}
