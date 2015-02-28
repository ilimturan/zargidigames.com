package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.Play;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import play.Logger;
import play.data.Form;

import java.io.File;
import java.util.List;

public class Admin extends Controller {

    private static final Form<Company> companyForm = Form.form(Company.class);
    private static final Form<User> adminSignUpForm = Form.form(User.class);
    private static final Form<AdminSignInForm> adminSignInForm = Form.form(AdminSignInForm.class);
    private static final Form<Post> adminPostForm = Form.form(Post.class);


    public static Result newadminform() {
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        return ok(views.html.adminNewForm.render(adminSignUpForm));


    }

    public static Result newadmin() {
        //TODO
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Form<User> boundForm = adminSignUpForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form bellow");
            return badRequest(views.html.adminNewForm.render(boundForm));
        } else {
            User newAdmin = boundForm.get();
            //newAdmin.save();
            Boolean result = newAdmin.create();
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
        if (session().get("connected") != null && session().get("connected").equals("ok")
                && session().get("id") != null && Integer.valueOf(session().get("id")) > 0) {
            return redirect(routes.Admin.adminpage());
        }

        return ok(views.html.adminLoginForm.render(adminSignInForm));


    }

    public static Result login() {

        //Login ise
        if (session().get("connected") != null && session().get("connected").equals("ok")
                && session().get("id") != null && Integer.valueOf(session().get("id")) > 0) {
            return redirect(routes.Admin.adminpage());
        }

        Form<AdminSignInForm> boundForm = adminSignInForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Email or pass error");
            return badRequest(views.html.adminLoginForm.render(adminSignInForm));
        } else {
            AdminSignInForm data = boundForm.get();
            User user = new User();
            Boolean result = user.login(data.emailAddress, data.passWord);

            if (result) {
                flash("success", "You are loggin");
                session().clear();
                session("connected", "ok");
                session("user", user.emailAddress);
                session("id", user.id.toString());
                return redirect(routes.Admin.adminpage());
            } else {
                flash("error", "Email or password not correct");
                return badRequest(views.html.adminLoginForm.render(boundForm));
            }

        }


    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Admin.loginform()
        );
    }

    public static Result adminpage() {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        String lg = session().get("connected");

        if ((lg != null) && lg.equals("ok")) {

            List<Product> products = Product.find.all();
            return ok(views.html.adminProductAll.render(products));

        } else {
            flash("error", "Please login");
            return redirect(routes.Admin.loginform());
        }

    }

    public static Result siteSetting() {
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Company company = Company.find.byId(1L);

        if (company == null) {
            company = new Company();
            company.save();
        }

        return ok(views.html.adminSiteSetting.render(company));
    }

    public static Result siteSettingUpdate() {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Form<Company> boundForm = companyForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Error");
            Company company = Company.find.byId(1L);
            return badRequest(views.html.adminSiteSetting.render(company));
        } else {
            Company company = boundForm.get();
            company.update();

            flash("success", "Updated");
            return redirect(routes.Admin.siteSetting());

        }
    }

    public static Result adminPostNew() {
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        return ok(views.html.adminPostNewForm.render(adminPostForm));

    }

    public static Result adminPostSave() {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Form<Post> boundForm = adminPostForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Error");
            return ok(views.html.adminPostNewForm.render(adminPostForm));
        } else {
            Post post = boundForm.get();
            post.isActive = false;
            //post.setUser(User.find.byId(Long.getLong(session().get("id"))));
            post.save();
            flash("success", "Post created");
            return redirect(routes.Admin.adminPostEdit(post.id));

        }

    }

    public static Result adminPostAll() {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        List<Post> posts = Post.find.all();

        return ok(views.html.adminPostAll.render(posts));
    }

    public static Result adminPostEdit(Long id) {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Post post = Post.find.byId(id);

        if (post == null) {
            badRequest("Post not found");
        }

        return ok(views.html.adminPostEditForm.render(post));
    }

    public static Result adminPostEditUpdate(Long id) {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        Post post = Post.find.byId(id);
        Form<Post> boundForm = adminPostForm.bindFromRequest();

        if (post == null || boundForm.hasErrors()) {
            flash("error", "Error");
            return ok(views.html.adminPostEditForm.render(post));
        } else {
            post = boundForm.get();
            post.update();
            flash("success", "Post updated");
            return redirect(routes.Admin.adminPostEdit(post.id));

        }
    }


    public static Result adminUploadFile() throws InterruptedException {

        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }
        ObjectNode result = Json.newObject();
        Http.MultipartFormData body = request().body().asMultipartFormData();


        if (body != null) {

            Http.MultipartFormData.FilePart zrgFile = body.getFile("zrgFile");
            File file = zrgFile.getFile();

            String zrgfileUploadPath = Play.application().configuration().getString("fileUploadPath");
            String zrgfileName = clearAndGenerateFileName(zrgFile.getFilename());
            file.renameTo(new File(zrgfileUploadPath, zrgfileName));


            ZrgFile newFile = new ZrgFile();
            newFile.fileName = zrgfileName;
            newFile.fileType = zrgFile.getContentType();
            newFile.isActive = true;
            newFile.fileSize = 1L;
            newFile.save();

            result.put("res", "ok");
            result.put("message", "File uploaded");
            result.put("path", "" + newFile.fileName);
            result.put("id", "" + newFile.id);


        } else {
            result.put("res", "error");
            result.put("message", "Error! File is empty");
            result.put("id", 0);
        }


        Thread.sleep(1000);
        return ok(result);
    }

    public static Result adminAllFile() {
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        List<ZrgFile> files = ZrgFile.find.all();

        return ok(views.html.adminZrgFileAll.render(files));
    }

    public static Result adminRemoveFile(long id) {
        //Login değilse
        if (session().get("connected") == null || session().get("id") == null) {
            return redirect(routes.Admin.loginform());
        }

        ZrgFile zrgfile = ZrgFile.find.byId(id);
        if(zrgfile != null){

            String oldFileName = zrgfile.fileName;
            String newFileName = clearAndGenerateRemovedFileName(zrgfile.fileName);

            zrgfile.isActive = false;
            zrgfile.fileName = newFileName;
            zrgfile.update();

            String zrgfilePath = Play.application().configuration().getString("fileUploadPath");
            File oldFile = new File(zrgfilePath, oldFileName);
            File newFile = new File(zrgfilePath, newFileName);
            oldFile.renameTo(newFile);

            if(newFile.exists()){
                oldFile.delete();
                flash("success", "File is delete (Db record make passive, real file name changed 'r_')");
            }else{
                flash("error", "File isn't delete");
            }
        }else{
            flash("error", "File not found");
        }

        return redirect(routes.Admin.adminAllFile());
    }

    private static String clearAndGenerateFileName(String fileName) {

        String newFileName = fileName.toLowerCase().replaceAll("[^a-zA-Z0-9\\._]+", "_");

        Long currentTimeStamp = System.currentTimeMillis() / 1000L;

        return currentTimeStamp + "_" + newFileName;
    }
    private static String clearAndGenerateRemovedFileName(String fileName) {

        return "r_" + fileName;
    }
}
