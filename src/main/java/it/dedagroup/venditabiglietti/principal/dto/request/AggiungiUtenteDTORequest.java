package it.dedagroup.venditabiglietti.principal.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggiungiUtenteDTORequest {
	
	@NotBlank(message = "Inserire un valore nel campo nome")
	private String nome;
	@NotBlank(message = "Inserire un valore nel campo cognome")
	private String cognome;
	@NotBlank(message="Il campo username non può essere vuoto")
	@Email
	private String email;
	@NotBlank(message="Il campo password non può essere vuoto")
	@Size(min = 8, message = "La password deve essere almeno di otto caratteri.")
	@Pattern(regexp = "^(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).*$", message = "La password deve contenere almeno un simbolo")
	@Pattern(regexp = "^(?=.*[0-9]).*$", message = "La password deve contenere almeno un numero")
	@Pattern(regexp = "^(?=.*[a-z]).*$", message = "La password deve contenere almeno una lettera minuscola")
	@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "La password deve contenere almeno una lettera maiuscola")
	private String password;
	@NotBlank(message="Il campo telefono non può essere vuoto")
	private String telefono;
	@NotNull(message="la data di nascita non è valida")
	private LocalDate data_di_nascita;

}
