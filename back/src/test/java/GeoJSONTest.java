import com.gis.application.model.Feature;
import com.gis.application.model.GeoJSON;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

public class GeoJSONTest {
    @Test
    void test(){
        Feature feature = new Feature("{\"type\": \"Point\", \"coordinates\": [102.0, 0.5]}",new HashMap<>());
        GeoJSON geoJSON = new GeoJSON(Collections.singletonList(feature));
        System.out.println(geoJSON);
    }
}
