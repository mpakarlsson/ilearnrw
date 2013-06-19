package ilearnrw.datalogger;

import ilearnrw.user.User;
interface ILoginProvider {

    /** \brief Authenticates a User.
     *
     */
    User getUser(String userName, String password);
}
