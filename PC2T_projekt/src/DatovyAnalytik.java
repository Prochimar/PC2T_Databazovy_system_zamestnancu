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
        return "Datový analytik";
    }

    @Override
    public void spustDovednost() {
        System.out.println("=== Dovednost: Datový analytik – počet spolupracovníků ===");



//TODO: implementace výpočtu nejvíce spolupracovníků společného s jiným zaměstnancem
	}
}