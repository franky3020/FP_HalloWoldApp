package tmpTest;

import org.junit.Test;
import franky.test.Franky_first_jar; // is in libs Myjat.jar

public class testimportJAR {

    @Test
    public void showYAYA_TEST() {
        Franky_first_jar franky_first_jar = new Franky_first_jar();
        System.out.println("This is jar function  --- start");
        franky_first_jar.showYAYA();
        System.out.println("This is jar function  --- end");
    }

}
