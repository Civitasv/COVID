package com.gis.application.mapper;

import com.gis.application.model.Virus;
import com.gis.application.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface VirusMapper {

    // 插入数据
    int insertVirus(Virus virus);

    // 按ID更新疫情数据
    int updateVirus(Virus virus);

    // 按ID删除疫情数据
    int deleteVirus(int id);

    // 按ID添加BASE64图片
    int addImage(int id, String base64);

    // 查询某时间戳的中国所有确诊数据
    List<VirusConfirmed> getAllChinaConfirmedVirusByTimestamp(int timestamp);

    // 查询某时间戳的世界所有确诊数据
    List<VirusConfirmed> getAllWorldConfirmedVirusByTimestamp(int timestamp);

    // 查询某时间戳的中国所有治愈数据
    List<VirusRecovered> getAllChinaRecoveredVirusByTimestamp(int timestamp);

    // 查询某时间戳的世界所有治愈数据
    List<VirusRecovered> getAllWorldRecoveredVirusByTimestamp(int timestamp);

    // 查询某时间戳的中国所有死亡数据
    List<VirusDeaths> getAllChinaDeathsVirusByTimestamp(int timestamp);

    // 查询某时间戳的世界所有死亡数据
    List<VirusDeaths> getAllWorldDeathsVirusByTimestamp(int timestamp);

    // 查询某时间戳的中国所有现存数据
    List<VirusActive> getAllChinaActiveVirusByTimestamp(int timestamp);

    // 查询某时间戳的世界所有现存数据
    List<VirusActive> getAllWorldActiveVirusByTimestamp(int timestamp);

    // 查询某时间戳的中国所有死亡率数据
    List<VirusDeathsRatio> getAllChinaDeathsRatioByTimestamp(int timestamp);

    // 查询某时间戳的世界所有死亡率数据
    List<VirusDeathsRatio> getAllWorldDeathsRatioByTimestamp(int timestamp);

    // 查询世界区域每日新增数据
    List<VirusNewIncrease> getWorldNewIncreaseVirusData();

    // 获取每日现存确诊、治愈、死亡数据
    List<VirusConfirmedRecovered> getConfirmedRecoveredVirusData();

    // 获取世界每个国家确诊数据
    List<Daily> getWorldConfirmedVirusData(int timestamp);

    // 获取世界每个国家治愈数据
    List<Daily> getWorldRecoveredVirusData(int timestamp);

    // 获取世界每个国家死亡数据
    List<Daily> getWorldDeathsVirusData(int timestamp);

    // 获取世界每个国家现存数据
    List<Daily> getWorldActiveVirusData(int timestamp);

    // 获取世界每个国家死亡率数据
    List<DeathsRatio> getWorldDeathsRatioData(int timestamp);

    // 获取国家每个省份确诊数据
    List<Daily> getCountryConfirmedVirusData(String country, int timestamp);

    // 获取国家每个省份治愈数据
    List<Daily> getCountryRecoveredVirusData(String country, int timestamp);

    // 获取国家每个省份死亡数据
    List<Daily> getCountryDeathsVirusData(String country, int timestamp);

    // 获取国家每个省份现存数据
    List<Daily> getCountryActiveVirusData(String country, int timestamp);

    // 获取国家每个省份死亡率数据
    List<DeathsRatio> getCountryDeathsRatioData(String country, int timestamp);

    // 获取中国每个省份确诊数据
    List<Daily> getChinaConfirmedVirusData(int timestamp);

    // 获取中国每个省份治愈数据
    List<Daily> getChinaRecoveredVirusData(int timestamp);

    // 获取中国每个省份死亡数据
    List<Daily> getChinaDeathsVirusData(int timestamp);

    // 获取中国每个省份现存数据
    List<Daily> getChinaActiveVirusData(int timestamp);

    // 获取中国每个省份现存数据
    List<DeathsRatio> getChinaDeathsRatioData(int timestamp);

    // 获取省份每个城市确诊数据
    List<Daily> getProvinceConfirmedVirusData(String country, String province, int timestamp);

    // 获取省份每个城市治愈数据
    List<Daily> getProvinceRecoveredVirusData(String country, String province, int timestamp);

    // 获取省份每个城市死亡数据
    List<Daily> getProvinceDeathsVirusData(String country, String province, int timestamp);

    // 获取省份每个现存死亡数据
    List<Daily> getProvinceActiveVirusData(String country, String province, int timestamp);

    // 获取省份每个现存死亡率数据
    List<DeathsRatio> getProvinceDeathsRatioData(String country, String province, int timestamp);

    // 获取中国省份每个城市确诊数据
    List<Daily> getChinaProvinceConfirmedVirusData(String province, int timestamp);

    // 获取中国省份每个城市治愈数据
    List<Daily> getChinaProvinceRecoveredVirusData(String province, int timestamp);

    // 获取中国省份每个城市死亡数据
    List<Daily> getChinaProvinceDeathsVirusData(String province, int timestamp);

    // 获取中国省份每个城市现存数据
    List<Daily> getChinaProvinceActiveVirusData(String province, int timestamp);

    // 获取中国省份每个城市现存数据
    List<DeathsRatio> getChinaProvinceDeathsRatioData(String province, int timestamp);
}
