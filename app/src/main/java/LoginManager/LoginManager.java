package LoginManager;

public class LoginManager {

    public boolean confirmLogin(String username, String password){

        // if ( 資料庫 找不到 username ) {
        //      return false
        // }

        String actual_password = "test_password" ; // 之後改成 db.getUserPassword( username )

        if ( password.equals(actual_password) ) {
            return true;
        }
        else {
            return false;
        }
    }

}
