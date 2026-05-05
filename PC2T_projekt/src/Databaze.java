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

		if (zamestnanecList.isEmpty()) {
			System.out.println("Nejsou žádní zaměstnanci typu "+typ);
			return;
		}

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
		Zamestnanec nej_vaz = null; 
		int pocet_nej_vaz = 0;
		int spatna_celkem = 0;
		int prumerna_celkem = 0;
		int dobra_celkem = 0;
		for (int i: prvkyDatabaze.keySet()) {
			Zamestnanec zamestnanec = prvkyDatabaze.get(i);
			System.out.print("ID: "+ i);
			System.out.println(", Jmeno: "+zamestnanec.getPrijmeni()+", "+zamestnanec.getJmeno());
			
			System.out.println("Spolupracovnici: ");
			if (zamestnanec.getSpoluprace().isEmpty()) {
				System.out.println("Neni k dispozici zadne spolupracovniky.");
			}	

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
			if (!spolupracovnici.isEmpty()) {
			System.out.print("Dobre spoluprace: "+ dobra);
			System.out.print(", Prumerne spoluprace: "+ prumerna);
			System.out.println(", Spatne spoluprace: "+ spatna);
			}


			if (spolupracovnici.size() > pocet_nej_vaz) {
				pocet_nej_vaz = spolupracovnici.size();
				nej_vaz = prvkyDatabaze.get(i);
			}
			spatna_celkem += spatna;
			prumerna_celkem += prumerna;
			dobra_celkem += dobra;
			}

		if (spatna_celkem > 0 || prumerna_celkem > 0 || dobra_celkem > 0) {
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
		}
		if (nej_vaz != null) {
			System.out.println("Pracovnik s nejvice spolupracemi:");
			System.out.print("ID: "+ nej_vaz.getID());
			System.out.println(", Jmeno: "+ nej_vaz.getPrijmeni()+", "+nej_vaz.getJmeno());
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
	
	public boolean ulozDoSouboru(String jmenoSouboru) {


		try (FileWriter fw=new FileWriter(jmenoSouboru);
				BufferedWriter bw=new BufferedWriter(fw))
			{
		
				bw.write("Pocet: "+prvkyDatabaze.size());
				bw.newLine();
				for (int i: prvkyDatabaze.keySet())
				{
					Zamestnanec aktualni_zamestnanec = prvkyDatabaze.get(i);
					if (aktualni_zamestnanec==null)
						break;
					bw.write("ID: "+aktualni_zamestnanec.getID());
					bw.write(" Jmeno: "+aktualni_zamestnanec.getJmeno());
					bw.write(" Prijmeni: "+aktualni_zamestnanec.getPrijmeni());
					bw.write(" Rocnik: "+aktualni_zamestnanec.getRokNarozeni());
					bw.write(" Typ: "+aktualni_zamestnanec.getTyp());
					
					List<Spoluprace> list_spolupraci = aktualni_zamestnanec.getSpoluprace();
					int pocet_spolupraci = list_spolupraci.size();
					bw.write(" Spoluprace: " + pocet_spolupraci);
					
					for (Spoluprace j: list_spolupraci) {
						bw.write(" ID: " + j.getIdKolegy());
						bw.write(" Uroven: " + j.getUroven().name());
					}
					bw.newLine();
				}
			} 
			catch (IOException e) {
				System.out.println("Nepodarilo se otevrit soubor");
				return false;
			}
			
			
			return true;
	}
	
	public boolean nacteniZeSouboru(String jmenoSoboru)
	{
		try (FileReader fr = new FileReader(jmenoSoboru);
			BufferedReader br=new BufferedReader(fr);
			Scanner sc=new Scanner(br))
		{
			sc.useLocale(Locale.US);
			String text=sc.next();
			if (text.compareTo("Pocet:")!=0||!sc.hasNextInt())
			{
				System.out.println("Chybny format dat");
				return false;
			}


			int pocet = sc.nextInt();
			while(sc.hasNext()&&sc.next().compareTo("ID:")==0)
			{
				int ID = sc.nextInt();
				sc.next();
				String Jmeno=sc.next();
				sc.next();
				String Prijmeni = sc.next();
				sc.next();
				if (!sc.hasNextInt())
				{
					System.out.println("Chybny format dat pro studenta "+Jmeno);
					sc.nextLine();
					continue;
				}
				int Rocnik=sc.nextInt();
				sc.next();
				String Typ = sc.next();
				Zamestnanec z;
				if (Typ.equals("DA")) {
					z = new DatovyAnalytik(ID, Jmeno, Prijmeni, Rocnik, prvkyDatabaze);
				}
				else {
					z = new BezpecnostniSpecialista(ID, Jmeno, Prijmeni, Rocnik, prvkyDatabaze);
				}
				sc.next();
				int pocet_spolupraci = sc.nextInt();
				for (int i = 0; i < pocet_spolupraci; i++) {
					sc.next();
					int IDkolegy = sc.nextInt();
					sc.next();
					String ur_str = sc.next();
					UrovenSpoluprace ur = UrovenSpoluprace.valueOf(ur_str);
					z.pridejSpolupraci(IDkolegy, ur);
				}
				prvkyDatabaze.put(ID, z);
			}
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("Soubor nelze otevrit");
			return false;
		} 
		catch (IOException e1) {
			System.out.println("Soubor nelze otevrit");
			return false;
		}
		
		
		return true;
	}


    private static final String DB_URL = "jdbc:sqlite:zamestnanci.db";

    public void ulozDoSQL() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS zamestnanci (" +
                "id INTEGER PRIMARY KEY, jmeno TEXT, prijmeni TEXT, rok INTEGER, typ TEXT)");
            conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS spoluprace (" +
                "id_zam INTEGER, id_kol INTEGER, uroven TEXT, " +
                "PRIMARY KEY (id_zam, id_kol))");

            PreparedStatement psZ = conn.prepareStatement(
                "INSERT OR REPLACE INTO zamestnanci VALUES (?,?,?,?,?)");
            PreparedStatement psS = conn.prepareStatement(
                "INSERT OR REPLACE INTO spoluprace VALUES (?,?,?)");

            for (Zamestnanec z : prvkyDatabaze.values()) {
                psZ.setInt(1, z.getID());
                psZ.setString(2, z.getJmeno());
                psZ.setString(3, z.getPrijmeni());
                psZ.setInt(4, z.getRokNarozeni());
                psZ.setString(5, z.getTyp());
                psZ.executeUpdate();

                for (Spoluprace s : z.getSpoluprace()) {
                    psS.setInt(1, z.getID());
                    psS.setInt(2, s.getIdKolegy());
                    psS.setString(3, s.getUroven().name());
                    psS.executeUpdate();
                }
            }
            System.out.println("Uloženo do SQL.");
        } catch (SQLException e) {
            System.out.println("SQL chyba při ukládání: " + e.getMessage());
        }
    }

    public void nactiZSQL() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            ResultSet rsZ = conn.createStatement().executeQuery(
                "SELECT * FROM zamestnanci");
            while (rsZ.next()) {
                int id = rsZ.getInt("id");
                String jmeno = rsZ.getString("jmeno");
                String prijmeni = rsZ.getString("prijmeni");
                int rok = rsZ.getInt("rok");
                String typ = rsZ.getString("typ");

                Zamestnanec z;
                if ("DA".equals(typ))
                    z = new DatovyAnalytik(id, jmeno, prijmeni, rok, prvkyDatabaze);
                else
                    z = new BezpecnostniSpecialista(id, jmeno, prijmeni, rok, prvkyDatabaze);

                prvkyDatabaze.put(id, z);
                if (id >= dalsiID) dalsiID = id + 1;
            }

            ResultSet rsS = conn.createStatement().executeQuery(
                "SELECT * FROM spoluprace");
            while (rsS.next()) {
                int idZ = rsS.getInt("id_zam");
                int idK = rsS.getInt("id_kol");
                UrovenSpoluprace ur = UrovenSpoluprace.valueOf(rsS.getString("uroven"));
                Zamestnanec z = prvkyDatabaze.get(idZ);
                if (z != null) z.pridejSpolupraci(idK, ur);
            }
            System.out.println("Načteno z SQL.");
        } catch (SQLException e) {
            System.out.println("SQL chyba nebo prázdná DB: " + e.getMessage());
        }
    }
}
