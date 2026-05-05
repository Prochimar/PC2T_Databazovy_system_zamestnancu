import java.util.Scanner;
public class main {

	public static void main(String[] args) {
	    Databaze db = new Databaze();
	    db.nactiZSQL();
	    Scanner sc = new Scanner(System.in);
	    boolean bezi = true;
		while (bezi) {
	            System.out.println("""
	                    \n=== MENU ===
	                    1. Přidat zaměstnance
	                    2. Přidat spolupráci
	                    3. Odebrat zaměstnance
	                    4. Vyhledat zaměstnance dle ID
	                    5. Spustit dovednost zaměstnance
	                    6. Abecední výpis podle přijmení a skupin
	                    7. Statistiky
	                    8. Počty zaměstnanců ve skupinách
	                    9. Uložit do souboru
	                    10. Načíst ze souboru
	                    11. Ukončit (+ uložení do SQL)""");
	            System.out.print("Volba: ");
	
			try {
				int volba;
				volba = sc.nextInt();
		
				switch (volba) {
					case 1 -> {
						System.out.println("Zadej jméno: ");
						String jmeno = sc.next();
						System.out.println("Zadej příjmení: ");
						String prijmeni = sc.next();
						System.out.println("Zadej rok narození: ");
						int rok = sc.nextInt();
					
		
						while (true) {
							System.out.println("Vyber typ: ");
							System.out.println("1. Bezpečnostní specialista");
							System.out.println("2. Datový analytik");
							try {
								int typ = sc.nextInt();
								if (typ == 1) {
									db.pridejSpecialistu(jmeno, prijmeni, rok);
								}
								else if (typ == 2) {
									db.pridejDatovehoAnalytika(jmeno, prijmeni, rok);
								} else if (typ != 1 && typ != 2) {
									System.out.println("Chyba: Musíš zadat 1 nebo 2!");
									continue;
								}
								break;
							} catch (Exception e) {
								sc.nextLine(); 
	                			System.out.println("Chyba: Musíš zadat číslo, ne text!");
								continue;
							}
							
						}
						System.out.println("Zaměstnanec byl úspěšně přidán.");
						break;
					}
					case 2 -> {
						System.out.println("Zadej ID zaměstnance, ke kterému chceš přidat spolupráci: ");
						int id = sc.nextInt();
						if (db.getZamestnanec(id) == null) {
							System.out.println("Zaměstnanec s tímto ID neexistuje.");
							break;
						}
	
						Zamestnanec zamestnanec = db.getZamestnanec(id);
						System.out.println("Zadej ID kolegy: ");
	
						int idKolegy = sc.nextInt();
						if (idKolegy == id) {
							System.out.println("Chyba: Nemůžeš přidat spolupráci sám se sebou!");
							break;
						} else if (db.getZamestnanec(idKolegy) == null) {
							System.out.println("Zaměstnanec s tímto ID neexistuje.");
							break;
						} 
						
						int ukonceni = 0;
						while (true) {
							System.out.println("Vyber úroveň spolupráce: ");
							System.out.println("1. Dobrá");
							System.out.println("2. Průměrná");
							System.out.println("3. Špatná");
							System.out.println("4. ukončit přidávání spolupráce");
							try {
								int uroven = sc.nextInt();
								if (uroven == 1) {
									zamestnanec.pridejSpolupraci(idKolegy,UrovenSpoluprace.DOBRA);
								}
								else if (uroven == 2) {
									zamestnanec.pridejSpolupraci(idKolegy,UrovenSpoluprace.PRUMERNA);
								}
								else if (uroven == 3) {
									zamestnanec.pridejSpolupraci(idKolegy,UrovenSpoluprace.SPATNA);
								}
								else if (uroven == 4) {
									ukonceni = 1;
									break;
								}
								break;
							} catch (Exception e) {
								sc.nextLine(); 
	                			System.out.println("Chyba: Musíš zadat číslo, ne text!");
								continue;
							}
						}
						if(ukonceni == 0) {
							System.out.println("Spolupráce byla úspěšně přidána.");
							break;
						}else {
							System.out.println("Přidávání spolupráce bylo ukončeno.");
							break;
						}
						
						
						
					}
					case 3 -> {
						System.out.println("Zadej ID zaměstnance: ");
						int id = sc.nextInt();
						if (db.getZamestnanec(id) == null) {
							System.out.println("Zaměstnanec s tímto ID neexistuje.");
							break;
						}
						try {
							db.removeZamestnanec(id);
							System.out.println("Zaměstnanec byl úspěšně odebrán.");
						} catch (Exception e) {
							System.out.println("Chyba při odebírání zaměstnance.");
							e.printStackTrace();
						}
						break;
						
					}
					case 4 -> {
						System.out.println("Zadej ID zaměstnance: ");
						int id = sc.nextInt();
						try {
							Zamestnanec zamestnanec = db.getZamestnanec(id);
							System.out.println("Načten zaměstnanec: ");
							System.out.println("ID: " +zamestnanec.getID());
							System.out.println("Jméno: " +zamestnanec.getJmeno());
							System.out.println("Příjmení: " +zamestnanec.getPrijmeni());
							System.out.println("Rok narození: " +zamestnanec.getRokNarozeni());
							String typ = zamestnanec.getTyp();
							if (typ == "DA") {
								typ = "Datový Analytik";
							}
							else {
								typ = "Bezpečnostní specialista";
							}
							System.out.println("Typ: " +typ);
						} catch (Exception e) {
							System.out.println("Zaměstnanec s tímto ID neexistuje.");
							break;
						}
						break;
					}
					case 5 -> {
						System.out.println("Zadej ID zaměstnance: ");
						int id = sc.nextInt();
						try {
							Zamestnanec zamestnanec = db.getZamestnanec(id);
							zamestnanec.spustDovednost();
						} catch (Exception e) {
							System.out.println();
							System.out.println("Zaměstnanec s tímto ID neexistuje.");
							break;
						}
						break;
					}
					case 6  -> {
						db.printAlphabetOrder("DA");
						db.printAlphabetOrder("BS");
						break;
					}
					case 7  -> {
						sc.nextLine();
						if(db.getPrvky().isEmpty()) {
							System.out.println("Databáze je prázdná, nelze zobrazit statistiky.");
							break;
						}
						db.kvalitaSpoluprace();
						break;
					}
					case 8  -> {
						int pocetDA = db.pocetPodleTypu("DA");
						int pocetBS = db.pocetPodleTypu("BS");
						System.out.println("Počet bezpečnostních specialistů: " +pocetBS);
						System.out.println("Počet datových analytiků: " +pocetDA);
						break;
						
					}
					case 9  -> {
						if (db.ulozDoSouboru("databaze.txt")) {
							System.out.println("Zápis se podařil");
						}
						else {
							System.out.println("Zápis se nezdařil");
						}
						break;
					}
					case 10 -> {
						if (db.nacteniZeSouboru("databaze.txt")) {
							System.out.println("Vypis se podařil");
						}
						else {
							System.out.println("Vypis se nezdařil");
						}
						break;
					}
					case 11 -> {
						db.ulozDoSQL();
						bezi = false;
						break;
					}
					default -> System.out.println("Neplatná volba.");
				}			
			} catch (Exception e) {
				sc.nextLine(); 
                System.out.println("Chyba: Musíš zadat číslo, ne text!");
			}
		}
		sc.close();
	}
}




