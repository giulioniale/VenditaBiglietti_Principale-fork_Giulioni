package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoMicroDTO {
    private long id;
    private LocalDate data;
    private LocalTime ora;
    private String descrizione;
    private boolean isCancellato;
    private long idLuogo;
    private long idManifestazione;
    private long version;
}
