package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.*;
import models.*;
import play.Play;
import play.mvc.*;
import play.data.Form;
import play.mvc.Http.*;
import java.io.File;
import java.util.List;
import play.libs.Json;

/**
 * Created by ilimturan on 21/02/15.
 */
@Security.Authenticated(SecuredUser.class)
public class DashBoard extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN-ZRGD";
    public static final String AUTH_TOKEN = "zrgdAuthToken";

    private static final Form<Post> adminPostForm = Form.form(Post.class);
    private static final Form<Company> companyForm = Form.form(Company.class);

    private static final Form<Product> productForm = Form.form(Product.class);
    private static final Form<ProductMarket> productMarketForm = Form.form(ProductMarket.class);

    public static Result adminProductNewForm() {

        return ok(views.html.adminProductNewForm.render(productForm));
    }


    public static Result adminProductAdd() {

        Form<Product> boundForm = productForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            //flash("error", "Please correct the form bellow");
            flash("error", boundForm.errors().toString());
            return badRequest(views.html.adminProductNewForm.render(boundForm));
        } else {

            Product newProduct = boundForm.get();
            newProduct.isActive = false; //
            ProductMarket market = new ProductMarket();
            market.save();
            newProduct.setMarket(market);
            newProduct.save();

            if (newProduct.id < 1) {
                flash("error", "New product can't create");
                return badRequest(views.html.adminProductNewForm.render(boundForm));
            } else {
                flash("success", "Successfully added new product");
                return redirect(routes.DashBoard.adminProductEdit(newProduct.id));
            }

        }
    }

    public static Result adminProductEdit(Long id) {

        Product product = Product.find.byId(id);

        if (product != null) {
            return ok(views.html.adminProductEditForm.render(product));
        }

        return badRequest("Product doesn't exist");

    }

    public static Result adminProductUpdate(Long id) {

        Form<Product> boundForm = productForm.bindFromRequest();
        Form<ProductMarket> marketForm = productMarketForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", boundForm.errors().toString());
            Product product = Product.find.byId(id);
            return badRequest(views.html.adminProductEditForm.render(product));
        } else if (marketForm.hasErrors()) {
            flash("error", marketForm.errors().toString());
            Product product = Product.find.byId(id);
            return badRequest(views.html.adminProductEditForm.render(product));
        } else {

            Product product = boundForm.get();

            ProductMarket productMarket = marketForm.get();
            if (productMarket.marketAndroidPricing > 0) {
                productMarket.marketAndroidIsFree = false;
            } else {
                productMarket.marketAndroidIsFree = true;
            }
            if (productMarket.marketIosPricing > 0) {
                productMarket.marketIosIsFree = false;
            } else {
                productMarket.marketIosIsFree = true;
            }

            productMarket.update();

            product.setMarket(productMarket);
            product.update();

            if (product.id == id) {
                flash("success", "Product updated");
                return redirect(routes.DashBoard.adminProductEdit(id));
            } else {
                flash("error", "Can't update product");
                return redirect(routes.DashBoard.adminProductEdit(id));
            }

        }

    }

    public static Result adminProductRemoveImage(Long imgId, Long imgType) {

        ObjectNode result = Json.newObject();

        if (imgType > 0 && imgType < 7) {

            if (imgType == 1) {
                ImageIcon img = ImageIcon.find.byId(imgId);
                img.isActive = false;
                img.update();

            } else if (imgType == 2) {
                ImageFutureGraphic img = ImageFutureGraphic.find.byId(imgId);
                img.isActive = false;
                img.update();

            } else if (imgType == 3) {
                ImagePromoGraphic img = ImagePromoGraphic.find.byId(imgId);
                img.isActive = false;
                img.update();

            } else if (imgType == 4) {
                ImageTvBanner img = ImageTvBanner.find.byId(imgId);
                img.isActive = false;
                img.update();

            } else if (imgType == 5) {
                ImagePhone img = ImagePhone.find.byId(imgId);
                img.isActive = false;
                img.update();
            } else if (imgType == 6) {
                ImageTablet img = ImageTablet.find.byId(imgId);
                img.isActive = false;
                img.update();
            }

            result.put("res", "ok");
            result.put("message", "removed");
            result.put("imgType", imgType);
            result.put("imgId", imgId);
        } else {
            result.put("res", "error");
            result.put("message", "Can't removed");
            result.put("imgType", imgType);
            result.put("imgId", 0);
        }

        return ok(result);
    }


    public static Result adminProductUploadImage(Long productId, Long productType) throws InterruptedException {

        ObjectNode result = Json.newObject();

        if (productType > 0 && productType < 7) {


            Product product = Product.find.byId(productId);
            MultipartFormData body = request().body().asMultipartFormData();


            if (body != null) {


                if (productType == 1) {

                    MultipartFormData.FilePart picture = body.getFile("icon");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/icon";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));


                    ImageIcon img = new ImageIcon();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.save();
                    product.setIcon(img);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                } else if (productType == 2) {

                    MultipartFormData.FilePart picture = body.getFile("futureGraphic");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/futureGraphic";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));

                    ImageFutureGraphic img = new ImageFutureGraphic();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.save();
                    product.setFutureGraphic(img);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                } else if (productType == 3) {

                    MultipartFormData.FilePart picture = body.getFile("promoGraphic");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/promoGraphic";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));

                    ImagePromoGraphic img = new ImagePromoGraphic();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.save();
                    product.setPromoGraphic(img);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                } else if (productType == 4) {

                    MultipartFormData.FilePart picture = body.getFile("tvBanner");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/tvBanner";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));

                    ImageTvBanner img = new ImageTvBanner();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.save();
                    product.setTvBanner(img);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                } else if (productType == 5) {

                    MultipartFormData.FilePart picture = body.getFile("imagePhone");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/phone";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));

                    ImagePhone img = new ImagePhone();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.setProduct(product);
                    img.save();
                    //List<ImagePhone> olds = product.getImagesPhone();
                    //olds.add(img);
                    //product.setImagesPhone(olds);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                } else if (productType == 6) {

                    MultipartFormData.FilePart picture = body.getFile("imageTablet");
                    File file = picture.getFile();

                    String imgUploadPath = Play.application().configuration().getString("imgUploadPath") + "/product/tablet";
                    String imgFileName = FileHelper.clearAndGenerateFileName(picture.getFilename());
                    file.renameTo(new File(imgUploadPath, imgFileName));

                    ImageTablet img = new ImageTablet();
                    img.fileName = imgFileName;
                    img.fileType = picture.getContentType();
                    img.size = 1;
                    img.isActive = true;
                    img.setProduct(product);
                    img.save();
                    //List<ImagePhone> olds = product.getImagesPhone();
                    //olds.add(img);
                    //product.setImagesPhone(olds);
                    product.update();

                    result.put("res", "ok");
                    result.put("message", "uploaded");
                    result.put("productType", productType);
                    result.put("path", "" + img.fileName);
                    result.put("id", "" + img.id);

                }

            } else {
                result.put("res", "error");
                result.put("message", "file is empty");
                result.put("productType", "undefined");
                result.put("id", 0);
            }

        } else {
            result.put("res", "error");
            result.put("message", "productType not found");
            result.put("productType", "undefined");
            result.put("id", 0);
        }
        Thread.sleep(1000);
        return ok(result);
    }


    public static Result adminProductAll() {

        List<Product> products = Product.find.all();

        return ok(views.html.adminProductAll.render(products));

    }


    public static Result adminProductUploadVideo(long productId) {

        ObjectNode result = Json.newObject();

        if (productId > 0) {

            Product product = Product.find.byId(productId);
            String[] videoCodes = request().body().asFormUrlEncoded().get("videoHtmlCode");

            if (product != null && videoCodes[0].length() > 2) {

                ProductVideo productVideo = new ProductVideo();
                productVideo.isActive = true;
                productVideo.videoHtmlCode = videoCodes[0];
                productVideo.setProduct(product);
                productVideo.save();
                product.update();

                result.put("res", "ok");
                result.put("message", "uploaded");
                result.put("videoId", productVideo.id);
                result.put("videoHtmlCode", productVideo.videoHtmlCode);


            } else {
                result.put("res", "error");
                result.put("message", "Can't removed, because vide not found");
                result.put("videoId", productId);
            }


        } else {
            result.put("res", "error");
            result.put("message", "Can't removed");
            result.put("videoId", 0);
        }

        return ok(result);
    }

    public static Result adminProductRemoveVideo(long videoId) {

        ObjectNode result = Json.newObject();

        if (videoId > 0) {

            ProductVideo video = ProductVideo.find.byId(videoId);
            if (video != null) {
                video.isActive = false;
                video.save();
                result.put("res", "ok");
                result.put("message", "removed");
                result.put("videoId", videoId);
            } else {
                result.put("res", "error");
                result.put("message", "Can't removed, because video not found");
                result.put("videoId", videoId);
            }


        } else {
            result.put("res", "error");
            result.put("message", "Can't removed");
            result.put("videoId", 0);
        }

        return ok(result);
    }

    public static Result adminpage() {


        List<Product> products = Product.find.all();
        return ok(views.html.adminProductAll.render(products));


    }

    public static Result siteSetting() {

        Company company = Company.find.byId(1L);

        if (company == null) {
            company = new Company();
            company.save();
        }

        return ok(views.html.adminSiteSetting.render(company));
    }

    public static Result siteSettingUpdate() {

        Form<Company> boundForm = companyForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Error");
            Company company = Company.find.byId(1L);
            return badRequest(views.html.adminSiteSetting.render(company));
        } else {
            Company company = boundForm.get();
            company.update();

            flash("success", "Updated");
            return redirect(routes.DashBoard.siteSetting());

        }
    }

    public static Result adminPostNew() {

        return ok(views.html.adminPostNewForm.render(adminPostForm));

    }

    public static Result adminPostSave() {

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
            return redirect(routes.DashBoard.adminPostEdit(post.id));

        }

    }

    public static Result adminPostAll() {

        List<Post> posts = Post.find.all();

        return ok(views.html.adminPostAll.render(posts));
    }

    public static Result adminPostEdit(Long id) {

        Post post = Post.find.byId(id);

        if (post == null) {
            badRequest("Post not found");
        }

        return ok(views.html.adminPostEditForm.render(post));
    }

    public static Result adminPostEditUpdate(Long id) {

        Post post = Post.find.byId(id);
        Form<Post> boundForm = adminPostForm.bindFromRequest();

        if (post == null || boundForm.hasErrors()) {
            flash("error", "Error");
            return ok(views.html.adminPostEditForm.render(post));
        } else {
            post = boundForm.get();
            post.update();
            flash("success", "Post updated");
            return redirect(routes.DashBoard.adminPostEdit(post.id));

        }
    }


    public static Result adminUploadFile() throws InterruptedException {

        ObjectNode result = Json.newObject();
        Http.MultipartFormData body = request().body().asMultipartFormData();


        if (body != null) {

            Http.MultipartFormData.FilePart zrgFile = body.getFile("zrgFile");
            File file = zrgFile.getFile();

            String zrgfileUploadPath = Play.application().configuration().getString("fileUploadPath");
            String zrgfileName = FileHelper.clearAndGenerateFileName(zrgFile.getFilename());
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


        Thread.sleep(500);
        return ok(result);
    }

    public static Result adminAllFile() {

        List<ZrgFile> files = ZrgFile.find.all();

        return ok(views.html.adminZrgFileAll.render(files));
    }

    public static Result adminRemoveFile(long id) {

        ZrgFile zrgfile = ZrgFile.find.byId(id);
        if (zrgfile != null) {

            String oldFileName = zrgfile.fileName;
            String newFileName = FileHelper.clearAndGenerateRemovedFileName(zrgfile.fileName);
            zrgfile.fileName = newFileName;
            zrgfile.isActive = false;
            zrgfile.update();

            String zrgfilePath = Play.application().configuration().getString("fileUploadPath");
            File oldFile = new File(zrgfilePath, oldFileName);
            File newFile = new File(zrgfilePath, newFileName);
            oldFile.renameTo(newFile);

            if (newFile.exists()) {
                oldFile.delete();
                flash("success", "File is delete (Db record make passive, real file name changed 'r_')");
            } else {
                flash("error", "File isn't delete");
            }
        } else {
            flash("error", "File not found");
        }

        return redirect(routes.DashBoard.adminAllFile());
    }


}
