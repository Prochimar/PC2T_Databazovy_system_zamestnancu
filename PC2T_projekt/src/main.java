import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

    Databaze db = new Databaze();
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
                    6. Abecední výpis podle skupin
                    7. Statistiky
                    8. Počty zaměstnanců ve skupinách
                    9. Uložit do souboru
                    10. Načíst ze souboru
                    11. Ukončit (+ uložení do SQL)""");
            System.out.print("Volba: ");

	int volba;
	//TODO: Přidat ošetření neplatného vstupu
	try {

	} catch (Exception e) {

	}



	switch (volba) {
		case 1 -> {}
		case 2 -> {}
		case 3 -> {}
		case 4 -> {}
		case 5 -> {}
		case 6  -> {}
		case 7  -> {}
		case 8  -> {}
		case 9  -> {}
		case 10 -> {}
		case 11 -> {}
		default -> System.out.println("Neplatná volba.");
	}			

}
}


