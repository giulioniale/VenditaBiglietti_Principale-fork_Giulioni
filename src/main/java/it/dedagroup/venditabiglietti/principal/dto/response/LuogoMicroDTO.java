package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuogoMicroDTO {
    private long id;
    private String riga1;
    private String riga2;
    private String provincia;
    private String cap;
    private String comune;
    private String nazionalita;
    private boolean isCancellato;
    private long version;
}
