package helpers;

import play.data.validation.Constraints;

/**
 * Created by ilimturan on 07/03/15.
 */
public class AdminSignUpForm {

    @Constraints.Required
    public String userName;
    @Constraints.Required
    public String fullName;
    @Constraints.Email
    public String emailAddress;
    @Constraints.Required
    public String passWord;
}
