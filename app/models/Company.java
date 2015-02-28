package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ilimturan on 24/02/15.
 */
@Entity
public class Company extends Model {

    @Id
    public Long cId;

    public Integer cSiteStatus = 1; //1 active, 2 deactive, 3 soon

    @Constraints.Required
    public String cName = "Zargidi Games";

    @Constraints.Required
    public String cLogo = "logo_zargidi_games.png";

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String cSlogan = "Living Through Applications and Games";

    @Constraints.Required
    public String cCity = "Istanbul";

    @Constraints.Required
    public String cCountry = "Turkey";

    @Column(columnDefinition = "TEXT")
    public String cAbout = "#";

    public String cFacebookAddress = "#";
    public String cTwitterAddress = "#";
    public String cLinkedinAddress = "#";
    public String cGoogleplusAddress = "#";
    public String cWebAddress = "#";
    public String cEmailAddress = "#";
    public String cRealAddress = "#";
    public String cPhone = "#";


    @Column(columnDefinition = "TEXT")
    public String cTitle = "Zargidi Games : Living Through Applications and Games";
    @Column(columnDefinition = "TEXT")
    public String cSiteDescription = "Living Through Applications and Games";
    @Column(columnDefinition = "TEXT")
    public String cSiteKeywords = "zargidi games, zargidi, application, game, android app, android game, ios app, ios game";

    public static Finder<Long, Company> find = new Finder<Long, Company>(
            Long.class, Company.class
    );

}
