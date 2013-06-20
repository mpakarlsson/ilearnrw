package ilearnrw.datalogger;

import ilearnrw.user.User;

public interface ILoginProvider {

    /** \brief Authenticates a User.
     *
     */
    User getUser(String username, String password);
    
    IUserAdministration getUserAdministration();
    
}