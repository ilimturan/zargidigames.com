package controllers;

import models.*;
import play.mvc.*;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        Company company = Company.find.byId(1L);
        if (company == null) {
            company = new Company();
            company.save();
        }
        List<Product> products = Product.find.all();
        List<Post> postsAll = Post.find.all(); //.orderBy("createdAt desc");

        List<Product> apps = new ArrayList<>();
        List<Product> games = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        for (Product product : products) {
            if (product.ptype == 1 && product.isActive && product.showMainPage) {
                apps.add(product);
            } else if (product.ptype == 2 && product.isActive && product.showMainPage) {
                games.add(product);
            }
        }
        for (Post post : postsAll) {
            if (post.isActive && post.showMainPage) {
                posts.add(post);
            }
        }

        return ok(index.render(company, products, apps, games, posts));
    }

    public static Result productDetail(Long id) {

        Company company = Company.find.byId(1L);
        List<Product> products = Product.find.all();
        List<Product> apps = new ArrayList<>();
        List<Product> games = new ArrayList<>();

        for (Product product : products) {
            if (product.ptype == 1 && product.isActive) {
                apps.add(product);
            } else if (product.ptype == 2 && product.isActive) {
                games.add(product);
            }

        }

        Product product = Product.find.byId(id);

        if (product == null || !product.isActive) {
            return badRequest("Not found product");
        }

        return ok(detail.render(company, products, apps, games, product));
    }

    public static Result productCategory(String type) {

        Company company = Company.find.byId(1L);
        List<Product> products = Product.find.all();

        List<Product> apps = new ArrayList<>();
        List<Product> games = new ArrayList<>();
        List<Product> categoryProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.ptype == 1 && product.isActive) {
                apps.add(product);
            } else if (product.ptype == 2 && product.isActive) {
                games.add(product);
            }

        }

        if (type.equals("games")) {
            categoryProducts = games;
        } else if (type.equals("applications")) {
            categoryProducts = apps;
        } else {
            return badRequest("Not found category");
        }
        String categoryTitle = type.toUpperCase();
        return ok(category.render(company, products, apps, games, categoryProducts, categoryTitle));

    }

    public static Result showPage(String type) {
        String pageTitle;
        if(type.equalsIgnoreCase("contact") || type.equalsIgnoreCase("about")){
            pageTitle = type.toUpperCase();
        }else{
            return badRequest("Not found page");
        }

        Company company = Company.find.byId(1L);
        List<Product> products = Product.find.all();
        List<Product> apps = new ArrayList<>();
        List<Product> games = new ArrayList<>();

        for (Product product : products) {
            if (product.ptype == 1 && product.isActive) {
                apps.add(product);
            } else if (product.ptype == 2 && product.isActive) {
                games.add(product);
            }

        }




        return ok(companyPage.render(company, products, apps, games, pageTitle));
    }


    public static Result showPost(long id) {

        Company company = Company.find.byId(1L);
        List<Product> products = Product.find.all();
        List<Product> apps = new ArrayList<>();
        List<Product> games = new ArrayList<>();

        for (Product product : products) {
            if (product.ptype == 1 && product.isActive) {
                apps.add(product);
            } else if (product.ptype == 2 && product.isActive) {
                games.add(product);
            }

        }

        List<Post> posts = Post.find.all();
        Post post = Post.find.byId(id);

        if (post == null || !post.isActive) {
            return badRequest("Not found post");
        }


        return ok(postDetail.render(company, products, apps, games, posts, post));

    }
}
