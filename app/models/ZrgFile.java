package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class ZrgFile extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MinLength(4)
    public String fileName = "not_found.gif";

    @Constraints.Required
    @Constraints.MinLength(4)
    public String fileType = "";

    @Constraints.Required
    @Constraints.MinLength(4)
    public Long fileSize = 0L;

    public Boolean isActive = false;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date createdAt = new Date();

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date modifiedAt = new Date();


    public static Finder<Long, ZrgFile> find = new Finder<Long, ZrgFile>(
            Long.class, ZrgFile.class
    );
}
