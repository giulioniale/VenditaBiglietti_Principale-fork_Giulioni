package it.dedagroup.venditabiglietti.principal.dto.response;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteMicroDTO {
    private long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private Ruolo ruolo;
    private String email;
    private String password;
    private String telefono;
    private boolean isAvailable;
}
