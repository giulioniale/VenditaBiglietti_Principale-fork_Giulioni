package it.dedagroup.venditabiglietti.principal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPSEDTORequest {
    private long idPse;
    private long idEvento;
    private long idSettore;
    private double prezzo;
}
