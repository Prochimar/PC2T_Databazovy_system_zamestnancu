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
        return "Bezpečnostní specialista";
    }

    /**
     * Rizikové skóre = počet_spolupracovníků * průměrná_váha_kvality
     * Váhy: DOBRA=1, PRUMERNA=2, SPATNA=3
     * Čím vyšší skóre, tím vyšší riziko.
     */
    @Override
    public void spustDovednost() {
        System.out.println("=== Dovednost: Bezpečnostní specialista – rizikové skóre ===");

        //TODO: implementace výpočtu rizikového skóre
	}
}