package it.dedagroup.venditabiglietti.principal.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import it.dedagroup.venditabiglietti.principal.model.Utente;


public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	public Optional<Utente> findByEmailAndPassword(String email, String password);
	public Optional<Utente> findByTelefono(String telefono);
	public Optional<Utente> findByDataDiNascita(LocalDate dataDiNascita);
	public Optional<Utente> findByNomeAndCognome(String nome, String cognome);
	public Optional<Utente> findByRuolo(Ruolo ruolo);
	public Optional<Utente> findByNomeAndCognomeAndIsCancellatoTrue(String nome, String cognome);

}
