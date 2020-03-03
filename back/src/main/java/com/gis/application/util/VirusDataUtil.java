package com.gis.application.util;

import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VirusDataUtil {

    public static int updateHistoryData(VirusService service) {
        // 读取excel
        // 设定Excel文件所在路径
        String excelFileName = "F:\\college\\大三下\\新冠疫情\\first.xlsx";
        // 读取Excel文件内容
        List<Virus> readResult = ExcelUtil.readExcel(excelFileName);
        for(int i=0;i<readResult.size();i++){
            service.insertHistoryVirus(readResult.get(i));
        }
        return 1;
    }

    public static int updateRecentData() {

        return 0;
    }
}
