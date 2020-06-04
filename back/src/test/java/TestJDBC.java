import com.gis.application.MyApplication;
import com.gis.application.service.VirusService;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class TestJDBC {
    @Autowired
    VirusService service;
    @Value("classpath:china.json")
    private Resource geojson;
    @Test
    public void test(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            System.out.println ((int)(calendar.getTime().getTime()/1000));
    }

    @Test
    public void test2() throws JSONException, IOException {
        List<HashMap<String, Object>> list = service.getProvinceData();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.put(jsonObject);
        }
        System.out.println(jsonArray);
        String coordiante = IOUtils.toString(geojson.getInputStream(), Charset.forName("UTF-8"));
        JSONObject jsonObject = new JSONObject(coordiante);
        JSONArray jsonArray1 = jsonObject.getJSONArray("features");
        for(int i=0;i<jsonArray1.length();i++){
            String name = jsonArray1.getJSONObject(i).getJSONObject("properties").getString("name");
            for(int j=0;j<jsonArray.length();j++){
                if(name.substring(0,2).equals(jsonArray.getJSONObject(j).getString("province").substring(0,2))){
                    jsonArray1.getJSONObject(i).getJSONObject("properties").put("confirmed",jsonArray.getJSONObject(j).getInt("confirmed"));
                    jsonArray1.getJSONObject(i).getJSONObject("properties").put("recovery",jsonArray.getJSONObject(j).getInt("recovery"));
                    jsonArray1.getJSONObject(i).getJSONObject("properties").put("deaths",jsonArray.getJSONObject(j).getInt("deaths"));
                }
            }
        }
        System.out.println(jsonObject);

    }
}
