package ilearnrw.datalogger;

import ilearnrw.user.User;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public interface ILoginProvider {

    /** \brief Authenticates a User.
     *
     */
    User getUser(String username, String password);
    
    IUserAdministration getUserAdministration();
    
}