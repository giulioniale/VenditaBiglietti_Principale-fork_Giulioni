package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManifestazioneMicroDTO {
    private Long id;
    private String nome;
    private Long categoria_id;
    private long utente_id;
    private boolean isCancellato;
    private long version;
}
