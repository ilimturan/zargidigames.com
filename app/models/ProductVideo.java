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
public class ProductVideo extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String videoHtmlCode = "";

    @Constraints.Required
    public String videoSource = ""; //youtube, vimeo

    public Boolean isActive= false;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date createdAt = new Date();

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date modifiedAt = new Date();

    @ManyToOne
    public Product product;

    public static Finder<Long, ProductVideo> find = new Finder<Long, ProductVideo>(
            Long.class, ProductVideo.class
    );

    public void setProduct(Product product) {
        this.product = product;
    }
}
