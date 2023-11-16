package it.dedagroup.venditabiglietti.principal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTORequest {

    @NotBlank(message="Il campo username non può essere vuoto")
    @Email
    private String email;
    @NotBlank(message="Il campo password non  può essere vuoto")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "La password deve avere almeno 8 caratteri. "
                    + "Non deve contenere spazi bianchi. "
                    + "Deve contenere almeno una lettera maiuscola.\r\n"
                    + "Deve contenere almeno una lettera minuscola.\r\n"
                    + "Deve contenere almeno un numero.\r\n"
                    + "Deve contenere almeno un carattere speciale tra @, #, $, %, ^, &, +, =.")
    private String password;


    public Boolean isRequestNull(){
        return this.email == null && this.password == null || this.email.isBlank() || this.password.isBlank();
    }
}
