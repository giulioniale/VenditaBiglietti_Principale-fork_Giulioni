package it.dedagroup.venditabiglietti.principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import it.dedagroup.venditabiglietti.principal.repository.UtenteRepository;

@SpringBootTest
@ContextConfiguration(classes = VenditaBigliettiPrincipalApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestRepositoryUtente {
	
	@Autowired
	UtenteRepository repo;
	
	@Test
	public void testFindAll() {
		assertEquals(6, repo.findAll().size());
		
	}
	
	@Test
	public void testFindById() {
		assertThat(repo.findById(1L).get())
		.isNotNull()
		.extracting("id","nome","cognome","email","password")
		.containsExactly(1L,"Mario","Rossi","mario.rossi@email.com","Abcd12$");
	}
	
//	@Test
//	public void testInsert() {
//		Utente u = new Utente();
//		u.setNome("Valerio");
//		u.setCognome("Scifoni");
//		u.setEmail("valerio.scifoni@email.com");
//		u.setPassword("Abcd12$");
//		u.setRuolo(Ruolo.CLIENTE);
//		u.setTelefono("3452674335");
//		u.setDataDiNascita(LocalDate.parse("1997-01-01"));
//		repo.save(u);
//		assertThat(repo.findByEmail("valerio.scifoni@email.com").get())
//		.isNotNull()
//		.extracting("nome","cognome","email","password","ruolo","dataDiNascita","telefono","isCancellato")
//		.containsExactly("Valerio","Scifoni","valerio.scifoni@email.com","Abcd12$",Ruolo.CLIENTE,"1997-01-01","3452674335",false);
//		
//	
//	}
}
