package it.dedagroup.venditabiglietti.principal.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggiungiUtenteDTORequest {
	
//	@NotNull(message = "Inserire un valore nel campo nome")
	private String nome;
//	@NotNull(message = "Inserire un valore nel campo cognome")
	private String cognome;
//	@NotEmpty(message="Il campo username non può essere vuoto")
//	@Email
	private String email;
//	@NotEmpty(message="Il campo password non  può essere vuoto")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
//			message = "La password deve avere almeno 8 caratteri. "
//			+ "Non deve contenere spazi bianchi. "
//			+ "Deve contenere almeno una lettera maiuscola.\r\n"
//			+ "Deve contenere almeno una lettera minuscola.\r\n"
//			+ "Deve contenere almeno un numero.\r\n"
//			+ "Deve contenere almeno un carattere speciale tra @, #, $, %, ^, &, +, =.")
	private String password;
//	@NotEmpty(message="Il campo telefono non può essere vuoto")
	private String telefono;
//	@NotBlank(message="la data di nascita non è valida")
	private LocalDate data_di_nascita;

}
