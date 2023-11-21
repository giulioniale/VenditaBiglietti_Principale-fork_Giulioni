package it.dedagroup.venditabiglietti.principal.dto.request;

import it.dedagroup.venditabiglietti.principal.model.Settore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManifestazioneStatisticheDTORequest {
    private long idManifestazione;
    private List<String> luoghi;
    private List<Settore> settoriIds;
}
