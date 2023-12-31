package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import javax.mail.MessagingException;

import controller.controller;
import model.istruttore;
import model.tariffario;

public class menuIstruttore {
	public void menu(String username) throws IOException, SQLException, MessagingException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner scan = new Scanner(System.in);
		controller c = new controller();
		int choice = 0;
		do {
			try {
				System.out.println("Benvenuto " + username + "!");
				System.out.println("Cosa vuoi fare?");
				System.out.println(
						"1) Crea una nuova prenotazione \n2) Modifica una tua penotazione \n3) Elimina una tua prenotazione \n4) Calcola stipendio \n5) Logout");
				choice = scan.nextInt();
				switch (choice) {
				case 1:
					c.nuovaPrenotazione(username);
					break;
				case 2:
					c.modificaPrenotazioneUtente(username);
					break;
				case 3:
					c.eliminaPrenotazioneUtente(username);
					break;
				case 4:
					tariffario t = new tariffario();
					String idString = username.substring(1); //TODO testa questo codice e valutare se togliere il metodo da istruttore e richiamarlo direttamente da menu istruttore
					int id = Integer.parseInt(idString);
					istruttore i = getIstruttore(id);
					float stipendio = t.calcolaStipendioIstruttore(i);
					System.out.println("Lo stipendio attuale ammonta a: " + stipendio);
					break;
				case 5:
					System.out.println("Logout effettuato.");
				}
			}
			catch (Exception e) {
				System.out.println("Si è verificato un errore durnte l'ultima operazione, si consiglia di ricontrollare i valori inseriti e di riprovare.");
			}
		} while (choice != 4);
	}
	
	private istruttore getIstruttore(int id) {
		controller c = new controller();
		istruttore i = c.selezionaIstruttore(id);
		return i;
	}
}
