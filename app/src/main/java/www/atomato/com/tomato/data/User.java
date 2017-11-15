package www.atomato.com.tomato.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15.
 */

public class User {
    private Map<String, String> maps = new HashMap<>();
    private String username;
    private String email;
    private String mobile;
    private String password;

    public User(String username, String email, String mobile, String password) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        maps.put("username", username);
        maps.put("email", email);
        maps.put("mobile", mobile);
        maps.put("password", password);
    }
    public Map<String,String> getMaps(){
        return maps;
    }
    @Override
    public String toString() {
        return
                "{\"username\":" + "\"" + username + "\"" + "," +
                        "\"email\":" + "\"" + email + "\"" + "," +
                        "\"mobile\":" + "\"" + mobile + "\"" + "," +
                        "\"password\":" + "\"" + password + "\"" +
                        "}";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
