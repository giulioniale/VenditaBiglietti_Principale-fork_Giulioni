package it.dedagroup.venditabiglietti.principal.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.repository.UtenteRepository;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;
import jakarta.transaction.Transactional;

@Service
public class UtenteServiceImpl implements UtenteServiceDef{

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public Utente findByEmailAndPassword(String email, String password) {
		return utenteRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}

	@Override
	public Utente findByTelefono(String telefono) {
		return utenteRepository.findByTelefono(telefono).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}

	@Override
	public Utente findByData_Di_Nascita(LocalDate data_di_nascita) {
		return utenteRepository.findByDataDiNascita(data_di_nascita).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}

	@Override
	public Utente findByNomeAndCognome(String nome, String cognome) {
		return utenteRepository.findByNomeAndCognome(nome, cognome).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}

	@Override
	public Utente findByRuolo(Ruolo ruolo) {
		return utenteRepository.findByRuolo(ruolo).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}

	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void aggiungiUtente(Utente utente) {
		utenteRepository.save(utente);
		
	}

	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public Utente modificaUtente(Utente utente) {
		return utenteRepository.save(utente);
	}

	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public Utente eliminaUtente(Utente utente) {
		utente.setCancellato(true);
		return utenteRepository.save(utente);
	}

}
