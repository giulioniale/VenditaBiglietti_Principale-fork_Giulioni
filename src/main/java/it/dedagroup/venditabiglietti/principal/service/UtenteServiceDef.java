package it.dedagroup.venditabiglietti.principal.service;

import java.time.LocalDate;

import it.dedagroup.venditabiglietti.principal.dto.request.LoginDTORequest;
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
	Utente eliminaUtente(long id);
	Utente findByEmail(String email);
	Utente login(String email,String password);
	Utente findById(long id);
	String disattivaAdmin(long id);
	//torna il token
	String login(LoginDTORequest request);
}
