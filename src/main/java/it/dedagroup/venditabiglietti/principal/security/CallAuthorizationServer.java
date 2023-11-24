package it.dedagroup.venditabiglietti.principal.security;

import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

//TODO nuovo service che chiama il microservizio
@Service
public class CallAuthorizationServer implements GeneralCallService {

    public Utente getUserDetail(String token){
        String path="http:localhost:80123/find/"+token;
        Utente u=callPost(path,null,null, Utente.class);
        return u;

    }

    public UserDetails findByEmail(String email) {
        String path="http:localhost:80123/email/"+email;
        Utente u=callPost(path,null,null, Utente.class);
        return u;
    }
}
