package com.include.easydocker.services;

import com.include.easydocker.classes.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UsersService {

    /* Este usuario se inicializa en la template home,
    * - si es un usuario que NO está registrado se inicializa en el controller: /user-temporal
    * - si es un usuario que SI está registrado se inicializa desde la base de datos,
    * o bien llamándolo (Sign In) o bien creándolo (Sign Up).
    *
    * Después de tener este objecto no se necesita llamar a la base de datos para pedir
    * más información.
    * */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
