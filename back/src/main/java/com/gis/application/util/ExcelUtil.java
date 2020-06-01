package com.gis.application.util;

import com.gis.application.model.Virus;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Description: 读取Excel内容
 */
public class ExcelUtil {

    private static Logger logger = Logger.getLogger(ExcelUtil.class.getName()); // 日志打印类

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {// xls文件
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {// xlsx文件
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param file 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Virus> readExcel(MultipartFile file) {

        Workbook workbook = null;
        InputStream inputStream = null;

        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);

            // 获取Excel工作簿
            inputStream = file.getInputStream();
            workbook = getWorkbook(inputStream, suffixName);

            // 读取excel中的数据
            List<Virus> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + file.getOriginalFilename() + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Virus> parseExcel(Workbook workbook) throws IOException {
        List<Virus> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = 2;
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                Virus resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }


    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static Virus convertRowToData(Row row) throws IOException {
        String[] provinces = {"河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建",
                "江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南",
                "陕西", "甘肃", "青海", "台湾", "内蒙古", "广西", "西藏", "宁夏", "新疆", "北京", "上海",
                "天津", "重庆", "香港", "澳门"};
        Virus resultData = new Virus();

        Cell cell;
        int cellNum = 0;
        // 获取时间
        cell = row.getCell(cellNum++);
        resultData.setPublicTime((int) (cell.getNumericCellValue()));
        // 获取地址
        cell = row.getCell(cellNum++);
        String province = "", city = "";
        province = cell.getStringCellValue();

        cell = row.getCell(cellNum++);
        city = cell.getStringCellValue();
        if("境外输入".equals(city.split("-")[0])){
            city = "";
        }
        boolean ifsuit = false;
        for (int j = 0; j < provinces.length; j++) {
            if (provinces[j].equals(province)) {
                ifsuit = true;
                resultData.setCountry("中国");
                resultData.setProvince(province);
                resultData.setCity(city);
                resultData.setDistrict("");
                break;
            }
        }
        if(!ifsuit){
            resultData.setCountry(province);
            resultData.setProvince("");
            resultData.setCity("");
            resultData.setDistrict("");
        }

        // 获取新增确诊
        cell = row.getCell(cellNum++);
        resultData.setNewDiagnosis((int) (cell.getNumericCellValue()));
        // 获取新增恢复
        cell = row.getCell(cellNum++);
        resultData.setNewRecovery((int) (cell.getNumericCellValue()));
        // 获取新增死亡
        cell = row.getCell(cellNum++);
        resultData.setNewDeath((int) (cell.getNumericCellValue()));
        // 获取lat，lng
        JSONObject lnglat = GaodeHttp.getPosition(resultData.getCountry()+resultData.getProvince()+resultData.getCity());
        // GCJ02转为WGS84坐标
        double[] wgs84 = Transform.transformGCJ02ToWGS84(lnglat.getDouble("lng"), lnglat.getDouble("lat"));
        resultData.setLat(wgs84[1]);
        resultData.setLng(wgs84[0]);
        resultData.setLocation(GISUtil.geometryToString(resultData.getLng(), resultData.getLat()));
        return resultData;
    }
}