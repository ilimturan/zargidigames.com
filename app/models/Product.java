package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class Product extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public Long ptype; // 1 app, 2 game

    @Constraints.Required
    @Constraints.MinLength(4)
    @Column(columnDefinition = "TEXT")
    public String title;

    @Constraints.Required
    @Constraints.MinLength(4)
    @Column(columnDefinition = "TEXT")
    public String descShort;

    @Constraints.Required
    @Constraints.MinLength(4)
    @Column(columnDefinition = "TEXT")
    public String descFull;

    @OneToMany
    public List<ImagePhone> imagesPhone;

    @OneToMany
    public List<ImageTablet> imagesTablet;

    @OneToMany
    public List<ProductVideo> videos;

    @OneToOne
    public ImageIcon icon;

    @OneToOne
    public ImageFutureGraphic futureGraphic;

    @OneToOne
    public ImagePromoGraphic promoGraphic;

    @OneToOne
    public ImageTvBanner tvBanner;

    @OneToOne
    public ProductMarket market;


    public Boolean isActive = false;
    public Boolean isSoon = false;
    @Column(columnDefinition = "TEXT")
    public String urlSlug = "#";
    public Boolean showMainPage = false;


    public static Finder<Long, Product> find = new Finder<Long, Product>(
            Long.class, Product.class
    );

    public void setId(Long id) {
        this.id = id;
    }

    public void setPtype(Long ptype) {
        this.ptype = ptype;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    public void setDescFull(String descFull) {
        this.descFull = descFull;
    }

    public void setImagesPhone(List<ImagePhone> imagesPhone) {
        this.imagesPhone = imagesPhone;
    }

    public void setImagesTablet(List<ImageTablet> imagesTablet) {
        this.imagesTablet = imagesTablet;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setFutureGraphic(ImageFutureGraphic futureGraphic) {
        this.futureGraphic = futureGraphic;
    }

    public void setPromoGraphic(ImagePromoGraphic promoGraphic) {
        this.promoGraphic = promoGraphic;
    }

    public void setTvBanner(ImageTvBanner tvBanner) {
        this.tvBanner = tvBanner;
    }

    public void setMarket(ProductMarket market) {
        this.market = market;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setIsSoon(Boolean isSoon) {
        this.isSoon = isSoon;
    }

    public void setUrlSlug(String urlSlug) {
        this.urlSlug = urlSlug;
    }

    public static void setFind(Finder<Long, Product> find) {
        Product.find = find;
    }

    public void setVideos(List<ProductVideo> videos) {
        this.videos = videos;
    }

    public void setShowMainPage(Boolean showMainPage) {
        this.showMainPage = showMainPage;
    }
}
