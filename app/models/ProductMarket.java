package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class ProductMarket extends Model {
    @Id
    public Long marketId;

    @Constraints.Required
    //@Constraints.MinLength(4)
    public String marketAndroidUrl = "#";
    @Constraints.Required
    //@Constraints.MinLength(4)
    public String marketAndroidIconUrl = "ic_android_market.png";
    public String marketAndroidVersion = "v1.0";
    public Boolean marketAndroidIsActive = false;
    public Boolean marketAndroidIsFree = true;
    public Double marketAndroidPricing = 0.0;

    @Constraints.Required
    //@Constraints.MinLength(4)
    public String marketIosUrl = "#";
    @Constraints.Required
    //@Constraints.MinLength(4)
    public String marketIosIconUrl = "ic_ios_market.png";
    public String marketIosVersion = "v1.0";
    public Boolean marketIosIsActive = false;
    public Boolean marketIosIsFree = true;
    public Double marketIosPricing = 0.0;


    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date createdAt = new Date();

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date modifiedAt = new Date();


    @OneToOne(mappedBy = "market")
    public Product product;


    public static Finder<Long, ProductMarket> find = new Finder<Long, ProductMarket>(
            Long.class, ProductMarket.class
    );

    public void setProduct(Product product) {
        this.product = product;
    }
}
