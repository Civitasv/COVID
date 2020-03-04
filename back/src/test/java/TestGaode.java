import com.gis.application.util.GaodeHttp;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class TestGaode {
    @Test
    public void testGaodeHttp() throws IOException {
        JSONObject jsonObject = GaodeHttp.getPosition("北京市朝阳区阜通东大街6号");
        System.out.println(jsonObject);
    }

    @Test
    public void test(){
    }
}
