import java.util.Map;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Zamestnanec implements Dovednost, Serializable {
  
	private int ID;
	private int rokNarozeni;
	private String jmeno;
	private String prijmeni;
	//private String typ;
	//private Map<Integer,String> Spolupracovnici; //pokud to chapu dobre, zamestnanec si eviduje ID a uroven spoluprace, ne celeho zamestnance
	private List<Spoluprace> spoluprace;


	public Zamestnanec(int ID, String jmeno, String prijmeni, int rokNarozeni ) {
		this.ID = ID;
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.rokNarozeni = rokNarozeni;
		this.spoluprace = new ArrayList<>();
		//this.typ = typ; //je dobre popremyslet, jeslti budeme zadavat typy zamnestnancu jako str nebo ne, prozatim delam jen tu databazi ale
	}


	
	public int getID() {
		return ID;
	}
	public int getRokNarozeni() {
		return rokNarozeni;
	}
	public String getJmeno() {
		return jmeno;
	}
	public String getPrijmeni() {
		return prijmeni;
	}
	public List<Spoluprace> getSpoluprace() {
		return spoluprace;
	}

	/* 
	public String getTyp() {
		return typ;
	}

	public Map<Integer,String> getSpolupracovnici() {
		return Spolupracovnici;
	}

	public void setSpolupracovnici(int ID, String ur_spol) {
		Spolupracovnici.put(ID, ur_spol);
	}
*/
	
    public void pridejSpolupraci(int idKolegy, UrovenSpoluprace uroven) {
        spoluprace.add(new Spoluprace(idKolegy, uroven));
    }

    public void odeberSpolupraci(int idKolegy) {
        spoluprace.removeIf(s -> s.getIdKolegy() == idKolegy);
    }

    // Vrátí typ jako řetězec pro výpis
    public abstract String getTyp();

    // Abstraktní metoda z rozhraní Dovednost – každá podtřída implementuje vlastní logiku
    @Override
    public abstract void spustDovednost();

    @Override
    public String toString() {
        return "ID: " + ID + ", " + prijmeni + " " + jmeno + ", nar. " + rokNarozeni + " [" + getTyp() + "]";
    }
}

