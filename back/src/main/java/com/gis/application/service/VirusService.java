package com.gis.application.service;

import com.gis.application.model.Virus;
import com.gis.application.vo.*;

import java.util.List;

public interface VirusService {
    // 插入数据
    int insertVirus(Virus virus);

    // 按ID更新疫情数据
    int updateVirus(Virus virus);

    // 按ID删除疫情数据
    int deleteVirus(int id);

    // 按时间戳查询所有确诊数据
    List<VirusConfirmed> getAllConfirmedVirusByTimestamp(int timestamp);

    // 按时间戳查询所有治愈数据
    List<VirusRecovered> getAllRecoveredVirusByTimestamp(int timestamp);

    // 按时间戳查询所有死亡数据
    List<VirusDeaths> getAllDeathsVirusByTimestamp(int timestamp);

    // 按时间戳查询世界每个国家现存数据
    List<VirusActive> getAllActiveVirusByTimestamp(int timestamp);

    // 按时间戳查询世界每个国家死亡率数据
    List<VirusDeathsRatio> getAllDeathsRatioByTimestamp(int timestamp);


    // 查询世界区域每日新增数据
    List<VirusNewIncrease> getWorldNewIncreaseVirusData();

    // 获取某国家每日新增确诊、治愈、死亡数据
    List<VirusNewIncrease> getCountryNewIncreaseVirusData(String country);

    // 获取某省份每日新增确诊、治愈、死亡数据（除中国）
    List<VirusNewIncrease> getProvinceNewIncreaseVirusData(String country, String state);

    // 获取某城市每日新增确诊、治愈、死亡数据（除中国）
    List<VirusNewIncrease> getCityNewIncreaseVirusData(String country, String state, String county);

    List<VirusConfirmedRecovered> getWorldConfirmedRecoveredVirusData();

    List<VirusConfirmedRecovered> getCountryConfirmedRecoveredVirusData(String country);

    // 获取某省份每日现存确诊、治愈、死亡数据（除中国）
    List<VirusConfirmedRecovered> getProvinceConfirmedRecoveredVirusData(String country, String state);

    // 获取某城市每日现存确诊、治愈、死亡数据（除中国）
    List<VirusConfirmedRecovered> getCityConfirmedRecoveredVirusData(String country, String state, String county);

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

    // 获取中国省级确诊数据
    List<VirusConfirmed> getChinaProvinceConfirmedVirusByTimestamp(int timestamp);

    // 获取中国某省份确诊数据
    List<VirusConfirmed> getChinaProvinceConfirmedVirusByTimestamp(String province, int timestamp);

    // 获取中国市级确诊数据
    List<VirusConfirmed> getChinaCityConfirmedVirusByTimestamp(String province, int timestamp);

    // 查询某时间戳的中国省级所有确诊\治愈\死亡数据，用于密度图可视化
    List<VirusDensity> getChinaProvinceDensityVirusByTimestamp(int timestamp);

    // 获取中国某省份所有确诊\治愈\死亡数据，用于密度图可视化
    List<VirusDensity> getChinaProvinceDensityVirusByTimestamp(String province, int timestamp);

    // 查询某省某时间戳的中国市级所有确诊\治愈\死亡数据，用于密度图可视化
    List<VirusDensity> getChinaCityDensityVirusByTimestamp(String province, int timestamp);
}
