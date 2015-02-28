package models;

import play.data.validation.Constraints;

/**
 * Created by ilimturan on 17/11/14.
 */
public class AdminSignInForm {

    @Constraints.Email
    public String emailAddress;
    @Constraints.Required
    public String passWord;


}
