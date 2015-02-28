package models;

import play.Logger;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Console;
import java.util.List;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class User extends Model {
    @Id
    public Long id;
    @Constraints.Required
    @Constraints.MinLength(4)
    public String userName;
    @Constraints.Required
    @Constraints.MinLength(4)
    public String fullName;
    @Constraints.Email
    public String emailAddress;
    @Constraints.Required
    @Constraints.MinLength(4)
    public String passWord;
    public Boolean isActive;

    @OneToMany
    public Post posts;


    public static Finder<Long,User> find = new Finder<Long,User>(
            Long.class, User.class
    );

    /**
     * for password hash and validation
     * can be use user.save() in controller
     * @return
     */
    public Boolean create() {
        User user = new User();
        user.userName = this.userName;
        user.fullName = this.fullName;
        user.emailAddress = this.emailAddress;
        user.passWord = this.passWord;
        user.isActive = true;
        //user.passwordHash =
        user.save();
        if(user.id > 0){
            return true;
        }
        return false;
    }

    public Boolean login(String emailAddress, String passWord) {

        User user = User.find.where().eq("emailAddress", emailAddress).findUnique();

        if(user != null){
            if ( (passWord.equals(user.passWord)) && (user.isActive)) {

                this.id = user.id;
                this.userName = user.userName;
                this.fullName = user.fullName;
                this.emailAddress = user.emailAddress;
                this.passWord = user.passWord;
                this.isActive = true;

                return true;
            } else {
                //Logger.error("ERROR: password not correct");
                //Logger.error("input: " + emailAddress + "***" + passWord);
                //Logger.error("db: " + user.toString());
                return false;
            }
        }
        //Logger.error("ERROR: Admin not found");
        return false;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", passWord='" + passWord + '\'' +
                ", isActive=" + isActive +

                '}';
    }
}
