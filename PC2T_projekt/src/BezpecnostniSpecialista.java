import java.util.List;
import java.util.Map;

public class BezpecnostniSpecialista extends Zamestnanec {
    private static final long serialVersionUID = 1L;

    private transient Map<Integer, Zamestnanec> databaze;

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni,
                                   Map<Integer, Zamestnanec> databaze) {
        super(id, jmeno, prijmeni, rokNarozeni);
        this.databaze = databaze;
    }

    public void setDatabaze(Map<Integer, Zamestnanec> databaze) {
        this.databaze = databaze;
    }

    @Override
    public String getTyp() {
        return "BS";
    }

    @Override
    public void spustDovednost() {
    	System.out.println("=== Dovednost: Bezpečnostní specialista – rizikové skóre ===");
        
        for (Spoluprace i: spoluprace) {
        	float rizikove_skore = 0.0f;
        	int id = i.getIdKolegy();
        	Zamestnanec zamestnanec = databaze.get(id);
        	System.out.print("Pracovník: " + zamestnanec.getJmeno() + " " + zamestnanec.getPrijmeni() + ", ID: " + zamestnanec.getID());
        	List<Spoluprace> list_spolupraci = zamestnanec.getSpoluprace();
        	int vaha = 0;
        	int pocet_spolupraci = 0;
        	for (Spoluprace j: list_spolupraci) {
        		pocet_spolupraci++;
        		switch (j.getUroven()) {
                case DOBRA: vaha += 1; break;
                case PRUMERNA: vaha += 1; break;
                case SPATNA: vaha += 3; break;
				}
        	}
        	rizikove_skore = vaha / pocet_spolupraci;
        	System.out.print(", rizikové skóre: " + rizikove_skore);
        	if (rizikove_skore <= (pocet_spolupraci * 2)) {
        		System.out.println(", nadprůměrně dobré");
        		continue;
        	}
        	System.out.println(", podprůměrné");
        	
        }
	}
}