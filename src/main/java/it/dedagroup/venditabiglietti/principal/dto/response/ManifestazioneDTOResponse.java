package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManifestazioneDTOResponse {

    private long id;
    private String nome;
    private long idCategoria;
    private long idUtente;
}
