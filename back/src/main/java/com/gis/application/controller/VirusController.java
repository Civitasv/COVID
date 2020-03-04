package com.gis.application.controller;

import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import com.gis.application.util.ExcelUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class VirusController {

    @Autowired
    VirusService service;

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空，请重新上传";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!("xls".equals(suffixName)||"xlsx".equals(suffixName))){
            return "文件格式不合法，请上传xls或xlsx格式的文件！";
        }
        // 读取Excel文件内容
        List<Virus> readResult = ExcelUtil.readExcel(file);
        // 返回不能为空
        if(readResult!=null) {
            for (int i = 0; i < readResult.size(); i++) {
                service.insertVirus(readResult.get(i));
            }
        }
        // 插入完成之后要对数据做处理，第一更新外国坐标数据，第二去除"地市级不详"，abroad.sql语句
        service.updateData();
        return "数据录入完成！";
    }

    @GetMapping("/getVirusByTime")
    public String getVirusByTime() {
        List<HashMap<String, Object>> list = service.getVirusByTime(43891);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = list.get(i);
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @GetMapping("/getAllVirus")
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
            jsonObject2.put("origin", hashMap.get("origin"));
            jsonObject2.put("new_diagnosis", hashMap.get("new_diagnosis"));
            jsonObject2.put("new_recovery", hashMap.get("new_recovery"));
            jsonObject2.put("new_death", hashMap.get("new_death"));
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
}
