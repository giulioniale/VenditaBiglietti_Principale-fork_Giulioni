package it.dedagroup.venditabiglietti.principal.serviceimpl;

import java.time.LocalDate;
import java.util.List;

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

	//TODO pulire i metodi togliendo le duplicazioni



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
		List<Utente> utenti = utenteRepository.findAll().stream().filter(u-> !u.isCancellato()).toList();
		for(Utente u : utenti){
			if(u.getEmail().equalsIgnoreCase(utente.getEmail())){
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già presente in db.");
			}
		}
		utenteRepository.save(utente);
		
	}

	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public Utente modificaUtente(Utente utente) {
		return utenteRepository.save(utente);
	}

	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public Utente eliminaUtente(long id) {
		Utente u = utenteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente con id "+ id + " non trovato"));
		u.setCancellato(true);
		return utenteRepository.save(u);
	}

	@Override
	public Utente findByEmail(String email) {
		return utenteRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun utente con questa email."));
	}
	@Override
	public Utente login(String email,String password){
		return utenteRepository.findByEmailAndPassword(email,password).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nessun utente trovato con queste credenziali"));
	}

	@Override
	public Utente findById(long id) {
		return utenteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente con id "+ id + " non trovato"));
	}

	@Override
	public String disattivaAdmin(long id) {
		Utente u = utenteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente con id "+ id + " non trovato"));
		if (!u.getRuolo().equals(Ruolo.ADMIN)) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"l'utente ha ruolo " + u.getRuolo() + ", impossibile disattivare ruolo ADMIN");
		} else {
			u.setRuolo(Ruolo.CLIENTE);
			modificaUtente(u);
		}
		return u.getEmail();
	}

}
