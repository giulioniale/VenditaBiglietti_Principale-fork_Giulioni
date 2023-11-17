package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LuogoDtoResponse {

    private long id;
    private String riga1;
    private String riga2;
    private String provincia;
    private String cap;
    private String comune;
    private String nazionalita;
    private List<Long> idEventi;
    private List<Long> idSettori;
}
