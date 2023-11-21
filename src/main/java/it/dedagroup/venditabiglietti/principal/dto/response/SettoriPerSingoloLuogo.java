package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettoriPerSingoloLuogo {
    private String settore;
    private long bigliettiComprati;
    private long bigliettiTotali;
    private double guadagnoBiglietti;
    private double prezzoAttuale;
}
