import com.gis.application.MyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class TestJDBC {

    @Test
    public void test(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            System.out.println ((int)(calendar.getTime().getTime()/1000));
    }

    @Test
    public void test2(){
    }
}
