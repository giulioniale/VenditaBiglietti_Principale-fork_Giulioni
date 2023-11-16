package it.dedagroup.venditabiglietti.principal.mapper;

import org.springframework.stereotype.Component;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import it.dedagroup.venditabiglietti.principal.model.Utente;

@Component
public class UtenteMapper {

	
	public Utente toUtenteCliente(AggiungiUtenteDTORequest dto) {
		Utente u= new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setDataDiNascita(dto.getData_di_nascita());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setTelefono(dto.getTelefono());
		u.setRuolo(Ruolo.CLIENTE);
		return u;
		}
	
	
	public Utente toUtenteVenditore(AggiungiUtenteDTORequest dto) {
		Utente u= new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setDataDiNascita(dto.getData_di_nascita());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setTelefono(dto.getTelefono());
		u.setRuolo(Ruolo.VENDITORE);
		return u;
		}
	
	public Utente toUtenteAdmin(AggiungiUtenteDTORequest dto) {
		Utente u= new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setDataDiNascita(dto.getData_di_nascita());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setTelefono(dto.getTelefono());
		u.setRuolo(Ruolo.ADMIN);
		return u;
		}
	
	public Utente toUtenteSuperAdmin(AggiungiUtenteDTORequest dto) {
		Utente u= new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setDataDiNascita(dto.getData_di_nascita());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setTelefono(dto.getTelefono());
		u.setRuolo(Ruolo.SUPER_ADMIN);
		return u;
		}
}
