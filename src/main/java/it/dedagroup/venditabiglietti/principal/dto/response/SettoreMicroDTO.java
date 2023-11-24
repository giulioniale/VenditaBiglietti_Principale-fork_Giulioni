package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettoreMicroDTO {
    private Long id;
    private String nome;
    private int capienza;
    private long idLuogo;
    private boolean isCancellato;
    private long version;
}
