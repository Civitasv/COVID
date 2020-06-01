package com.gis.application.controller;

import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import com.gis.application.util.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * restful style
 */
@RestController
@RequestMapping("/virus")
public class VirusController {

    @Autowired
    VirusService service;

    @Value("classpath:static/data/abroad.json")
    private Resource lnglat;

    @Value("classpath:static/data/china.json")
    private Resource clnglat;

    /**
     * 文件上传
     *
     * @param file excel数据 .xls .xlsx
     * @return 上传状态
     */
    @PostMapping("/")
    public String upload(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空，请重新上传";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!("xls".equals(suffixName) || "xlsx".equals(suffixName))) {
            return "文件格式不合法，请上传xls或xlsx格式的文件！";
        }
        // 读取Excel文件内容
        List<Virus> readResult = ExcelUtil.readExcel(file);
        // 返回不能为空
        if (readResult != null) {
            for (int i = 0; i < readResult.size(); i++) {
                service.insertVirus(readResult.get(i));
            }
        }
        return "数据录入完成！";
    }

    /**
     * 按时间查询数据
     *
     * @param time 时间
     * @return json数据
     */
    @GetMapping(value = "/{time}")
    public String getVirusByTime(@PathVariable int time) {
        List<HashMap<String, Object>> list = service.getVirusByTime(time);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    /**
     * 得到所有数据
     *
     * @return json数据
     */
    @GetMapping("/")
    public String getAllVirus() {
        List<HashMap<String, Object>> list = service.getAllVirus();
        // 组装GeoJSON数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "FeatureCollection");
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("features", jsonArray);
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("type", "Feature");
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("country", hashMap.get("country"));
            jsonObject2.put("province", hashMap.get("province"));
            jsonObject2.put("city", hashMap.get("city"));
            jsonObject2.put("district", hashMap.get("district"));
            jsonObject2.put("new_diagnosis", hashMap.get("new_diagnosis"));
            jsonObject2.put("new_recovery", hashMap.get("new_recovery"));
            jsonObject2.put("new_death", hashMap.get("new_death"));
            jsonObject2.put("description", hashMap.get("description"));

            jsonObject1.put("properties", jsonObject2);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("type", "Point");
            float[] lnglat = {(float) hashMap.get("lng"), (float) hashMap.get("lat")};
            jsonObject3.put("coordinates", lnglat);
            jsonObject1.put("geometry", jsonObject3);
            jsonArray.put(jsonObject1);
        }
        return jsonObject.toString();
    }

    /**
     * 更新ID
     */
    @GetMapping("/id")
    public void updateID() {
        // 得到有几个
        List<HashMap<String, Object>> list = service.getAllVirus();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            service.updateID(i + 1, (Integer) (hashMap.get("id")));
        }
    }


    /**
     * 更新经纬度，上传数据处理用
     */
    @GetMapping("/lnglat")
    public void updateLngLat() {
        // 得到所有的数据
        List<HashMap<String, Object>> list = service.getAllVirus();
        try {
            String coordiante = IOUtils.toString(lnglat.getInputStream(), Charset.forName("UTF-8"));
            JSONObject jsonObject = new JSONObject(coordiante);
            //System.out.println(jsonObject);
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> hashMap = list.get(i);
                // 得到country
                String country = hashMap.get("country").toString();
                // 在json中循环
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray(country);
                    service.updateVirusByID((int) hashMap.get("id"), jsonArray.getDouble(0), jsonArray.getDouble(1));
                } catch (Exception e) {
                    if (!"中国".equals(country))
                        System.out.println("error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询为0的，并更新，上传数据处理用
     */
    @GetMapping("/latEqualZero")
    public void updateLatEqualZero() {
        List<HashMap<String, Object>> list = service.selectLatLngEqualZero();
        try {
            String coordiante = IOUtils.toString(clnglat.getInputStream(), Charset.forName("UTF-8"));
            JSONObject jsonObject = new JSONObject(coordiante);
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> hashMap = list.get(i);
                // province
                String province = hashMap.get("province").toString();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray(province);
                    service.updateVirusByID((int) hashMap.get("id"), jsonArray.getDouble(0), jsonArray.getDouble(1));
                } catch (Exception e) {
                    System.out.println("error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得每个国家的数据
     *
     * @return json数据
     */
    @GetMapping("/country")
    public String getCountryData() {
        List<HashMap<String, Object>> list = service.getVirusDivideByCountry();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    /**
     * 获取每天的数据
     *
     * @return json数据
     */
    @GetMapping("/day")
    public String getVirusDivideByTime() {
        List<HashMap<String, Integer>> list = service.getVirusDivideByTime();
        // 解析excel时间
        JSONArray jsonArray = new JSONArray(list);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Date date = TimeUtil.transfromNumToDate(jsonObject.getInt("public_time"));
            jsonObject.remove("public_time");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("public_date", sdf.format(date));
        }
        return jsonArray.toString();
    }

    /**
     * 获取每天累积的数据
     *
     * @return json数据
     */
    @GetMapping("/dayAdded")
    public String getAllVirusDivideByTime() {
        List<HashMap<String, Integer>> list = service.getVirusDivideByTime();
        // 解析excel时间
        JSONArray jsonArray = new JSONArray(list);
        int confirmed = 0;
        int recovery = 0;
        int deaths = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Date date = TimeUtil.transfromNumToDate(jsonObject.getInt("public_time"));
            confirmed += jsonObject.getInt("new_diagnosis");
            recovery += jsonObject.getInt("new_recovery");
            deaths += jsonObject.getInt("new_death");
            jsonObject.remove("public_time");
            jsonObject.remove("new_diagnosis");
            jsonObject.remove("new_recovery");
            jsonObject.remove("new_death");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("public_date", sdf.format(date));
            jsonObject.put("confirmed", confirmed);
            jsonObject.put("recovery", recovery);
            jsonObject.put("deaths", deaths);
            //System.out.println(sdf.format(date) + "," + confirmed + "," + recovery + "," + deaths);
        }
        return jsonArray.toString();
    }


    @GetMapping("/test")
    public void test() {
        // 得到所有的数据
        List<HashMap<String, Object>> list = service.getAllVirus();
        // 循环
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            // 得到country
            String country = hashMap.get("country").toString();
            String province = hashMap.get("province").toString();
            int id = (int) hashMap.get("id");
            String city = hashMap.get("city").toString();
            if ("中国".equals(country)) {
                // 获取lat，lng
                JSONObject lnglat = GaodeHttp.getPosition(country + province + city);
                // GCJ02转为WGS84坐标
                double[] wgs84 = Transform.transformGCJ02ToWGS84(lnglat.getDouble("lng"), lnglat.getDouble("lat"));
                service.updateVirusByID(i + 1, wgs84[0], wgs84[1]);
            }
        }
    }
}
