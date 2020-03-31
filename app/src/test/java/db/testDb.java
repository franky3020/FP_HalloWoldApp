package db;

import android.content.res.AssetManager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import android.content.Context;
public class testDb {

    @Test
    public void connect_userData_Test() {
        try {
            Properties props = get_db_properties("src/test/assets/jdbc.properties");

            //Class 的靜態 forName() 方法實現動態加載類別
            Class.forName(props.getProperty("db.driver"));

            // ?useSSL=false 這參數 是要解決 WARN: Establishing SSL connection without server's identity verification
            Connection con = DriverManager.getConnection(props.getProperty("db.url") + "?useSSL=false", props.getProperty("db.user"), props.getProperty("db.password"));

            Statement st = con.createStatement();

            String query = " SELECT userPhone FROM userData WHERE userName = 'test_username' ";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                String userPhone = rs.getString("userPhone");
                assertEquals("0911111111", userPhone);
            }

        }catch(Exception ex){
            System.out.println("Error: "+ex);
            fail();
        }
    }

    private Properties get_db_properties(String file_path) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(file_path));
            return props;
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
        }
        return props;
    }

    @Test
    public void propTest() {

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/test/assets/jdbc.properties"));
            String userName = props.getProperty("db.user");
            assertEquals("root", userName);
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            fail();
        }
    }

}
