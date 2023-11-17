package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigliettoMicroDTO {
    private long id;
    private LocalDate dataAcquisto;
    private double prezzo;
    private String seriale;
    private boolean IsCancellato;
    private long version;
    private long idUtente;
    private long idPrezzoSettoreEvento;
}
