package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class Post extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MinLength(4)
    public String title = "";

    @Constraints.Required
    @Constraints.MinLength(4)
    @Column(columnDefinition = "TEXT")
    public String text = "";

    @Column(columnDefinition = "TEXT")
    public String keywords = "";

    public Boolean isActive;

    @Column(columnDefinition = "TEXT")
    public String urlSlug = "#";

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date createdAt = new Date();

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date modifiedAt = new Date();

    @ManyToOne
    public User user;

    public Boolean showMainPage = false;

    public static Finder<Long,Post> find = new Finder<Long,Post>(
            Long.class, Post.class
    );

    public void setUser(User user) {
        this.user = user;
    }
}
