import java.io.*;
import java.sql.*;
import java.util.*;

public class Databaze {
	private Map<Integer,Zamestnanec> prvkyDatabaze;
	private int dalsiID;
	
	public Databaze() {
		prvkyDatabaze = new HashMap<>();
		dalsiID = 1;
	}
	
	public Map<Integer, Zamestnanec> getPrvky() {
        return prvkyDatabaze;
    }



    public Zamestnanec pridejSpecialistu(String jmeno, String prijmeni, int rok) {
        BezpecnostniSpecialista z = new BezpecnostniSpecialista(dalsiID++, jmeno, prijmeni, rok, prvkyDatabaze);
        prvkyDatabaze.put(z.getID(), z);
        return z;
    }
 
    public Zamestnanec pridejDatovehoAnalytika(String jmeno, String prijmeni, int rok) {
        DatovyAnalytik z = new DatovyAnalytik(dalsiID++, jmeno, prijmeni, rok, prvkyDatabaze);
        prvkyDatabaze.put(z.getID(), z);
        return z;
    }



	public boolean removeZamestnanec(int ID) {
		if (prvkyDatabaze.remove(ID)==null)
			return false;
		return true;
	}

	public Zamestnanec getZamestnanec(int ID) {
		return prvkyDatabaze.get(ID);
	}
	
	//seznam zamestnancu stejného typu
	private ArrayList<Zamestnanec> sameTyp(String typ) {
		ArrayList<Zamestnanec> typList = new ArrayList<Zamestnanec>();
		for (int i: prvkyDatabaze.keySet()) {
			Zamestnanec zamestnanec = prvkyDatabaze.get(i);
			if (zamestnanec.getTyp() == typ) {
				typList.add(zamestnanec);
			}
		}
		return typList;
	}
	
	public void printAlphabetOrder(String typ) {
		ArrayList<String> prijmeniList = new ArrayList<String>();
		ArrayList<Zamestnanec> zamestnanecList = sameTyp(typ);
		for (Zamestnanec i: zamestnanecList) {
			prijmeniList.add(i.getPrijmeni());
		}
		Collections.sort(prijmeniList);
		
		for (String i: prijmeniList) {
			for(Zamestnanec j: zamestnanecList) {
				if (j.getPrijmeni() == i) {
					System.out.print("ID: "+j.getID());
					System.out.print(", Jmeno: "+j.getJmeno());
					System.out.print(", Prijmeni: "+j.getPrijmeni());
					System.out.println(", Rok narozeni: "+j.getRokNarozeni());
					break;
				}
				
			}
		}
	}
	
	public void kvalitaSpoluprace() {
		Zamestnanec nej_vaz = null; //zmenit jakmile budeme delat vyjimky
		int pocet_nej_vaz = 0;
		int spatna_celkem = 0;
		int prumerna_celkem = 0;
		int dobra_celkem = 0;
		for (int i: prvkyDatabaze.keySet()) {
			Zamestnanec zamestnanec = prvkyDatabaze.get(i);
			System.out.print("ID: "+ i);
			System.out.println(", Jmeno: "+zamestnanec.getPrijmeni()+", "+zamestnanec.getJmeno());
			System.out.println("Spolupracovnici: ");
			List<Spoluprace> spolupracovnici = zamestnanec.getSpoluprace();
			
			int spatna = 0;
			int prumerna = 0;
			int dobra = 0;
			for (Spoluprace spoluprace: spolupracovnici) {
				System.out.print("ID: "+ spoluprace.getIdKolegy());
				Zamestnanec spolupracovnik = prvkyDatabaze.get(spoluprace.getIdKolegy());
				System.out.print(", Jmeno: "+spolupracovnik.getPrijmeni()+", "+spolupracovnik.getJmeno());
				System.out.println(", kvalita spoluprace: "+ spoluprace.getUroven());
				switch (spoluprace.getUroven()) {
                    case DOBRA: dobra++; break;
                    case PRUMERNA: prumerna++; break;
                    case SPATNA: spatna++; break;
					}
				}
			System.out.print("Dobre spoluprace: "+ dobra);
			System.out.print(", Prumerne spoluprace: "+ prumerna);
			System.out.println(", Spatne spoluprace: "+ spatna);
			
			if (spolupracovnici.size() > pocet_nej_vaz) {
				pocet_nej_vaz = spolupracovnici.size();
				nej_vaz = prvkyDatabaze.get(i);
			}
			spatna_celkem += spatna;
			prumerna_celkem += prumerna;
			dobra_celkem += dobra;
			}
		
		System.out.print("Prevazujici kvalita spoluprace: ");
		if (spatna_celkem > prumerna_celkem && spatna_celkem > dobra_celkem) {
			System.out.println("Spatna");
		}
		else if (prumerna_celkem > dobra_celkem) {
			System.out.println("Prumerna");
		}
		else {
			System.out.println("Dobra");
		}
		
		System.out.println("Pracovnik s nejvice spolupracemi:");
		System.out.print("ID: "+ nej_vaz.getID());
		System.out.println(", Jmeno: "+ nej_vaz.getPrijmeni()+", "+nej_vaz.getJmeno());
		}
	
	public int pocetPodleTypu(String typ) {
		ArrayList<Zamestnanec> zamestnanecList = sameTyp(typ);
		int pocet = zamestnanecList.size();
		return pocet;
	}
}
