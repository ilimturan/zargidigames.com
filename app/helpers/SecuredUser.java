package helpers;

import models.Product;
import play.mvc.Security;
import controllers.*;
import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;

import java.util.List;

/**
 * Created by ilimturan on 07/03/15.
 */
public class SecuredUser extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {

        User user = null;
        String[] authTokenHeaderValues = ctx.request().headers().get(Admin.AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            user = User.findByAuthToken(authTokenHeaderValues[0]);
            if (user != null) {
                ctx.args.put("user", user);
                return user.emailAddress;
            }
        } else {
            String user_is_connected = DashBoard.session().get("user_is_connected");
            String user_email = DashBoard.session().get("user_email");

            if ((user_is_connected != null) && (user_email != null) && user_is_connected.equals("ok")) {

                user = User.find.where().eq("emailAddress", user_email).findUnique();

                if (user != null && user.isActive) {
                    return user.emailAddress;
                }

            }
        }
        return null;


    }

    @Override
    public Result onUnauthorized(Context ctx) {

        return redirect(routes.Admin.loginform());

    }
}