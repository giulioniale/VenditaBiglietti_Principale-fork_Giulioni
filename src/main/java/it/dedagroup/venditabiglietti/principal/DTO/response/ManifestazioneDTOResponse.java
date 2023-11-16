package it.dedagroup.venditabiglietti.principal.DTO.response;

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
    private List<Long> idEventi;
    private long idUtente;
}
