package it.dedagroup.venditabiglietti.principal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyBigliettoDTORequest {
    private long id;
    private LocalDate dataAcquisto;
    private double prezzo;
    private String nSeriale;
    private boolean isCancellato;
    private long idUtente;
    private long idPrezzoSettoreEvento;
}
