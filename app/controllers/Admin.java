package controllers;

import helpers.AdminSignInForm;
import models.*;
import helpers.*;
import play.mvc.*;
import play.data.Form;

public class Admin extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN-ZRGD";
    public static final String AUTH_TOKEN = "zrgdAuthToken";

    private static final Form<AdminSignUpForm> adminSignUpForm = Form.form(AdminSignUpForm.class);
    private static final Form<AdminSignInForm> adminSignInForm = Form.form(AdminSignInForm.class);

    public static Result newadminform() {

        return ok(views.html.adminNewForm.render(adminSignUpForm));

    }

    public static Result newadmin() {

        Form<AdminSignUpForm> boundForm = adminSignUpForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form bellow");
            return badRequest(views.html.adminNewForm.render(boundForm));
        } else {
            User user = new User();
            user.userName = boundForm.get().userName;
            user.fullName = boundForm.get().fullName;
            user.emailAddress = boundForm.get().emailAddress;
            user.passWord = boundForm.get().passWord;

            Boolean result = user.create();
            if (!result) {
                flash("error", "New admin can't create");
                return badRequest(views.html.adminNewForm.render(boundForm));
            } else {
                flash("success", "Successfully added new admin");
                return redirect(routes.Admin.loginform());
            }

        }
    }

    public static Result loginform() {
        //Login ise
        if (session().get("user_is_connected") != null && session().get("user_is_connected").equals("ok")
                && session().get("user_email") != null) {
            return redirect(routes.DashBoard.adminpage());
        }

        return ok(views.html.adminLoginForm.render(adminSignInForm));

    }

    public static Result login() {

        //Login ise
        if (session().get("user_is_connected") != null && session().get("user_is_connected").equals("ok")
                && session().get("user_email") != null) {
            return redirect(routes.DashBoard.adminpage());
        }


        Form<AdminSignInForm> boundForm = adminSignInForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Email or pass error");
            return badRequest(views.html.adminLoginForm.render(adminSignInForm));
        } else {
            AdminSignInForm data = boundForm.get();
            User user = User.findByEmailAndPassword(data.emailAddress, data.passWord);

            if (user != null && user.isActive) {

                flash("success", "You are loggin");
                session().clear();
                session("user_is_connected", "ok");
                session("user_email", user.emailAddress);
                session("user_id", user.id.toString());
                String authToken = user.createToken();
                user.update();
                response().setCookie(AUTH_TOKEN, authToken);

                return redirect(routes.DashBoard.adminpage());
            } else {
                flash("error", "Email or password not correct");
                return badRequest(views.html.adminLoginForm.render(boundForm));
            }

        }


    }

    public static User getUser() {
        //return (User) Http.Context.current().args.get("user");
        User user = null;
        String user_is_connected = DashBoard.session().get("user_is_connected");
        String user_email = DashBoard.session().get("user_email");

        if ((user_is_connected != null) && (user_email != null) && user_is_connected.equals("ok")) {

            user = User.find.where().eq("emailAddress", user_email).findUnique();

            if (user != null && user.isActive) {
                return user;
            }

        }
        return null;
    }

    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteAuthToken();
        session().clear();

        flash("success", "You've been logged out");
        return redirect(
                routes.Admin.loginform()
        );
    }


}
