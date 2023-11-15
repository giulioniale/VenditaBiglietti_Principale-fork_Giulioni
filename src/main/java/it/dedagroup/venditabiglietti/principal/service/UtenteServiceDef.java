package it.dedagroup.venditabiglietti.principal.service;

import java.time.LocalDate;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import it.dedagroup.venditabiglietti.principal.model.Utente;

public interface UtenteServiceDef {
	
	Utente findByEmailAndPassword(String email, String password);
	Utente findByTelefono(String telefono);
	Utente findByData_Di_Nascita(LocalDate data_di_nascita);
	Utente findByNomeAndCognome(String nome, String cognome);
	Utente findByRuolo(Ruolo ruolo);
	void aggiungiUtente(Utente utente);
	Utente modificaUtente(Utente utente);
	Utente eliminaUtente(Utente utente);

}
