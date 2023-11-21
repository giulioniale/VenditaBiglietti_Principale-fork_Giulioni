package it.dedagroup.venditabiglietti.principal.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AggiungiCategoriaDtoRequest {
	
	@NotEmpty(message="Il campo Nome non può essere vuoto")
    @Pattern(regexp = "[A-Za-z]+(\\s[A-Za-z]+)*", message = "Il campo deve essere una frase di lettere")
	private String nome;

}
