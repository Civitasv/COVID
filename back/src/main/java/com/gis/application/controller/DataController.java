package com.gis.application.controller;

import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import com.gis.application.util.ExcelUtil;
import com.gis.application.util.GISUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    VirusService service;

    // second
    @RequestMapping("/second")
    public int insert(){
        String [] provinces = {"河北","山西","辽宁","吉林","黑龙","江苏","浙江","安徽","福建",
                "江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南",
                "陕西","甘肃","青海","台湾","内蒙","广西","西藏","宁夏","新疆","北京","上海",
                "天津","重庆","香港","澳门"};
        List<HashMap<String, Object>> hashMap = service.getAllOrigin();
        for(int i=0;i<hashMap.size();i++){
            String origin = hashMap.get(i).get("origin").toString();
            int id =(Integer)hashMap.get(i).get("id");
            String test = origin.substring(0,2);
            String renew = "";
            boolean ifSuit = false;
            for(int j=0;j<provinces.length;j++){
                if(provinces[j].equals(test)){
                    ifSuit = true;
                    if(j!=4&&j!=23) {
                        renew = "中国," + provinces[j] + "," + origin.substring(2)+",";
                        break;
                    }
                    else if(j==4){
                        renew = "中国,"+"黑龙江"+","+origin.substring(3)+",";
                        break;
                    }
                    else if(j==23){
                        renew = "中国,"+"内蒙古"+","+origin.substring(3)+",";
                        break;
                    }
                }
            }
            if(ifSuit) {
                service.updateVirusOriginByID(renew, id);
            }
        }
        return 0;
    }

    @RequestMapping("/third")
    public int update(){
        List<HashMap<String, Object>> hashMap = service.getAllOrigin();
        for(int i=0;i<hashMap.size();i++){
            String origin = hashMap.get(i).get("origin").toString();
            int id =(Integer)hashMap.get(i).get("id");

            if(origin.length()>3) {
                String test = origin.substring(origin.length() - 3, origin.length() - 1);
                String test2 = origin.substring(origin.length() - 2);
                String renew = "";
                boolean ifSuit = false;
                if ("NA".equals(test)) {
                    renew = origin.substring(0, origin.length() - 3) + ",";
                    ifSuit = true;
                }
                if ("NA".equals(test2)) {
                    renew = origin.substring(0, origin.length() - 2);
                    ifSuit = true;
                }
                if (ifSuit) {
                    service.updateVirusOriginByID(renew, id);
                }
            }
        }
        return 0;
    }

    // addData，不过暂时不准备继续添加新数据了，用这些数据就能做很多东西了
    @RequestMapping("/addData")
    public void addData(){
        // 读取excel
        // 设定Excel文件所在路径
        String excelFileName = "F:\\college\\大三下\\新冠疫情\\first.xlsx";
        // 读取Excel文件内容
        List<Virus> readResult = ExcelUtil.readExcel(excelFileName);
        for(int i=0;i<readResult.size();i++){
            service.insertHistoryVirus(readResult.get(i));
        }

    }


    @RequestMapping("/get")
    public String get() {
        List<HashMap<String, Object>> list = service.getVirusByTime(43891);

        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<list.size();i++){
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping("/getAllVirus")
    public String getData() throws IOException {
        List<HashMap<String, Object>> list = service.getAllVirus();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","FeatureCollection");
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("features",jsonArray);
        for(int i=0;i<list.size();i++){
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("type","Feature");
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("origin",hashMap.get("origin"));
            jsonObject2.put("new_diagnosis",hashMap.get("new_diagnosis"));
            jsonObject2.put("new_recovery",hashMap.get("new_recovery"));
            jsonObject2.put("new_death",hashMap.get("new_death"));
            jsonObject1.put("properties",jsonObject2);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("type","Point");
            float[] lnglat = {(float)hashMap.get("lng"),(float)hashMap.get("lat")} ;
            jsonObject3.put("coordinates",lnglat);
            jsonObject1.put("geometry",jsonObject3);
            jsonArray.put(jsonObject1);
        }
        return jsonObject.toString();
    }
}
