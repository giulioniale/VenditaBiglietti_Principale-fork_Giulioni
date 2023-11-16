package it.dedagroup.venditabiglietti.principal.exception.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorMessageListDTOResponse {
    private List<ErrorMessageDTOResponse> violations=new ArrayList<>();
}
