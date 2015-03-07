package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.UUID;
import org.mindrot.jbcrypt.*;

/**
 * Created by ilimturan on 17/11/14.
 */
@Entity
public class User extends Model {
    @Id
    public Long id;

    @Column(unique = true)
    @Constraints.Required
    @Constraints.MinLength(4)
    public String userName;

    @Constraints.Required
    @Constraints.MinLength(4)
    public String fullName;

    @Column(unique = true)
    @Constraints.Email
    public String emailAddress;

    @Constraints.Required
    @Constraints.MinLength(4)
    public String passWord;

    public Boolean isActive;

    @OneToMany
    public Post posts;

    public String authToken;

    public static Finder<Long,User> find = new Finder<Long,User>(
            Long.class, User.class
    );


    public Boolean create() {
        User user = new User();
        user.userName = this.userName;
        user.fullName = this.fullName;
        user.emailAddress = this.emailAddress;
        user.passWord = BCrypt.hashpw(passWord, BCrypt.gensalt());
        //user.passWord = getSha512(this.passWord);
        user.isActive = true;
        user.authToken = createToken();
        user.save();
        if(user.id > 0){
            return true;
        }
        return false;
    }

    public static User findByEmailAndPassword(String emailAddress, String passWord) {

        User user = User.find.where().eq("emailAddress", emailAddress).findUnique();
        if (user != null && (user.isActive) && BCrypt.checkpw(passWord, user.passWord)) {
            return user;
        }
        return null;
    }

    public static User findByAuthToken(String token) {

        User user = User.find.where().eq("authToken", token).findUnique();

        if((user != null) && (user.isActive) ){
            return user;
        }
        return null;
    }

    public String createToken() {
        this.authToken = UUID.randomUUID().toString();
        return this.authToken;
    }

    public void deleteAuthToken() {
        this.authToken = null;
        save();
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
                ", posts=" + posts +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
