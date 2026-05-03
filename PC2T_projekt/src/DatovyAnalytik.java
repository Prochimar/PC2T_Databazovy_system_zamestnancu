import java.util.List;
import java.util.Map;

public class DatovyAnalytik extends Zamestnanec {
    private static final long serialVersionUID = 1L;

    private transient Map<Integer, Zamestnanec> databaze;

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni,
                                   Map<Integer, Zamestnanec> databaze) {
        super(id, jmeno, prijmeni, rokNarozeni);
        this.databaze = databaze;
    }

    public void setDatabaze(Map<Integer, Zamestnanec> databaze) {
        this.databaze = databaze;
    }

    @Override
    public String getTyp() {
        return "DA";
    }

    @Override
    public void spustDovednost() {
        System.out.println("=== Dovednost: Datový analytik – počet spolupracovníků ===");
        int pocet_spolupracovniku = 0;
        int idZamestnance = 0;
        for (int i: databaze.keySet()) {
        	Zamestnanec zamestnanec = databaze.get(i);
        	if (zamestnanec.getID() == ID) {
        		continue;
        	}
        	List<Spoluprace> list_spolupraci = zamestnanec.getSpoluprace();
        	int aktualni_pocet_spol = 0;
        	for (Spoluprace j: spoluprace) {
        		for (Spoluprace k: list_spolupraci) {
        			if (j.getIdKolegy() == k.getIdKolegy()) {
        				aktualni_pocet_spol++;
        			}
        		}
        	}
        	if (aktualni_pocet_spol > pocet_spolupracovniku) {
        		pocet_spolupracovniku = aktualni_pocet_spol;
        		idZamestnance = zamestnanec.getID();
        	}
        }
        Zamestnanec zamestnanec = databaze.get(idZamestnance);
        System.out.println("Zaměstnanec s nejvyšším počtem společných spolupracovníků: "+ zamestnanec.getJmeno() + " " + zamestnanec.getPrijmeni() + ", ID: " + idZamestnance);
        System.out.println("Počet společných spolupracovníků: " + pocet_spolupracovniku);
	}
}