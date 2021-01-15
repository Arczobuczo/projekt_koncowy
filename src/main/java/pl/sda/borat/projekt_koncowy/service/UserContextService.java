package pl.sda.borat.projekt_koncowy.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {

    public String getCurrentlyLoggedUserEmail(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();       //"hold" state alike Session
        if (authentication instanceof AnonymousAuthenticationToken){
            return null;
        }
        return authentication.getName();
    }

}