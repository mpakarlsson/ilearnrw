package ilearnrw.datalogger;

import ilearnrw.user.UserProfile;
interface ILoginProvider {

    /** \brief Authenticates a User.
     *
     */
    UserProfile getProfile(String UserName, String Password);

}
