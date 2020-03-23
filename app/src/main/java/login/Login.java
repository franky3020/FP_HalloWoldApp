package login;


public class Login {
    String username = "aaaa";
    String password = "bbbb";

    public String ConfirmLogin(String s1,String s2){
        if(s1.equals(username)&&s2.equals(password)){
            return "correct";
        }
        else {
            return "Wrong username or password"
        }

    }




}
