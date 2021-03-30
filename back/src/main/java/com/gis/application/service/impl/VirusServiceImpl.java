package com.gis.application.service.impl;

import com.gis.application.mapper.VirusMapper;
import com.gis.application.model.Constants;
import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import com.gis.application.vo.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VirusServiceImpl implements VirusService {

    // 此种方式注入的sqlSession实质上为sqlSessionTemplate，不需要手动关闭
    private final SqlSession sqlSession;

    public VirusServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int insertVirus(Virus virus) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.insertVirus(virus);
    }

    @Override
    public int updateVirus(Virus virus) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateVirus(virus);
    }

    @Override
    public int deleteVirus(int id) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.deleteVirus(id);
    }

    @Override
    public int addImage(int id, String base64) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.addImage(id, base64);
    }

    @Override
    public List<VirusConfirmed> getAllConfirmedVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        List<VirusConfirmed> all = mapper.getAllChinaConfirmedVirusByTimestamp(timestamp);
        List<VirusConfirmed> worldAll = mapper.getAllWorldConfirmedVirusByTimestamp(timestamp);
        all.addAll(worldAll);
        return all;
    }

    @Override
    public List<VirusRecovered> getAllRecoveredVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        List<VirusRecovered> all = mapper.getAllChinaRecoveredVirusByTimestamp(timestamp);
        List<VirusRecovered> worldAll = mapper.getAllWorldRecoveredVirusByTimestamp(timestamp);
        all.addAll(worldAll);
        return all;
    }

    @Override
    public List<VirusDeaths> getAllDeathsVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        List<VirusDeaths> all = mapper.getAllChinaDeathsVirusByTimestamp(timestamp);
        List<VirusDeaths> worldAll = mapper.getAllWorldDeathsVirusByTimestamp(timestamp);
        all.addAll(worldAll);
        return all;
    }

    @Override
    public List<VirusActive> getAllActiveVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        List<VirusActive> all = mapper.getAllChinaActiveVirusByTimestamp(timestamp);
        List<VirusActive> worldAll = mapper.getAllWorldActiveVirusByTimestamp(timestamp);
        all.addAll(worldAll);
        return all;
    }

    @Override
    public List<VirusDeathsRatio> getAllDeathsRatioByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        List<VirusDeathsRatio> all = mapper.getAllChinaDeathsRatioByTimestamp(timestamp);
        List<VirusDeathsRatio> worldAll = mapper.getAllWorldDeathsRatioByTimestamp(timestamp);
        all.addAll(worldAll);
        return all;
    }

    @Override
    public List<VirusNewIncrease> getWorldNewIncreaseVirusData() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldNewIncreaseVirusData();
    }

    @Override
    public List<VirusNewIncrease> getCountryNewIncreaseVirusData(String country) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getCountryNewIncreaseVirusData(country);
    }

    @Override
    public List<VirusNewIncrease> getProvinceNewIncreaseVirusData(String country, String state) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceNewIncreaseVirusData("中国", state);
        else return mapper.getProvinceNewIncreaseVirusData(country, state);
    }

    @Override
    public List<VirusNewIncrease> getCityNewIncreaseVirusData(String country, String state, String county) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaCityNewIncreaseVirusData("中国", state, county);
        else return mapper.getCityNewIncreaseVirusData(country, state, county);
    }

    @Override
    public List<VirusConfirmedRecovered> getWorldConfirmedRecoveredVirusData() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldConfirmedRecoveredVirusData();
    }

    @Override
    public List<VirusConfirmedRecovered> getCountryConfirmedRecoveredVirusData(String country) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getCountryConfirmedRecoveredVirusData(country);
    }

    @Override
    public List<VirusConfirmedRecovered> getProvinceConfirmedRecoveredVirusData(String country, String state) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceConfirmedRecoveredVirusData("中国", state);
        else return mapper.getProvinceConfirmedRecoveredVirusData(country, state);
    }

    @Override
    public List<VirusConfirmedRecovered> getCityConfirmedRecoveredVirusData(String country, String state, String county) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaCityConfirmedRecoveredVirusData("中国", state, county);
        else return mapper.getCityConfirmedRecoveredVirusData(country, state, county);
    }

    @Override
    public List<Daily> getWorldConfirmedVirusData(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldConfirmedVirusData(timestamp);
    }

    @Override
    public List<Daily> getWorldRecoveredVirusData(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldRecoveredVirusData(timestamp);
    }

    @Override
    public List<Daily> getWorldDeathsVirusData(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldDeathsVirusData(timestamp);
    }

    @Override
    public List<Daily> getWorldActiveVirusData(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldActiveVirusData(timestamp);
    }

    @Override
    public List<DeathsRatio> getWorldDeathsRatioData(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldDeathsRatioData(timestamp);
    }

    @Override
    public List<Daily> getCountryConfirmedVirusData(String country, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaConfirmedVirusData(timestamp);
        else return mapper.getCountryConfirmedVirusData(country, timestamp);
    }

    @Override
    public List<Daily> getCountryRecoveredVirusData(String country, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaRecoveredVirusData(timestamp);
        else return mapper.getCountryRecoveredVirusData(country, timestamp);
    }

    @Override
    public List<Daily> getCountryDeathsVirusData(String country, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaDeathsVirusData(timestamp);
        else return mapper.getCountryDeathsVirusData(country, timestamp);
    }

    @Override
    public List<Daily> getCountryActiveVirusData(String country, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaActiveVirusData(timestamp);
        else return mapper.getCountryActiveVirusData(country, timestamp);
    }

    @Override
    public List<DeathsRatio> getCountryDeathsRatioData(String country, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaDeathsRatioData(timestamp);
        else return mapper.getCountryDeathsRatioData(country, timestamp);
    }

    @Override
    public List<Daily> getProvinceConfirmedVirusData(String country, String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceConfirmedVirusData(province, timestamp);
        else return mapper.getProvinceConfirmedVirusData(country, province, timestamp);
    }

    @Override
    public List<Daily> getProvinceRecoveredVirusData(String country, String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceRecoveredVirusData(province, timestamp);
        else return mapper.getProvinceRecoveredVirusData(country, province, timestamp);
    }

    @Override
    public List<Daily> getProvinceDeathsVirusData(String country, String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceDeathsVirusData(province, timestamp);
        else return mapper.getProvinceDeathsVirusData(country, province, timestamp);
    }

    @Override
    public List<Daily> getProvinceActiveVirusData(String country, String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceActiveVirusData(province, timestamp);
        else return mapper.getProvinceActiveVirusData(country, province, timestamp);
    }

    @Override
    public List<DeathsRatio> getProvinceDeathsRatioData(String country, String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        if ("China".equals(country)) return mapper.getChinaProvinceDeathsRatioData(province, timestamp);
        else return mapper.getProvinceDeathsRatioData(country, province, timestamp);
    }

    @Override
    public List<VirusConfirmed> getChinaProvinceConfirmedVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaProvinceConfirmedVirusByTimestamp(timestamp);
    }

    @Override
    public List<VirusConfirmed> getChinaProvinceConfirmedVirusByTimestamp(String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaProvinceOnlyConfirmedVirusByTimestamp(province, timestamp);
    }

    @Override
    public List<VirusConfirmed> getChinaCityConfirmedVirusByTimestamp(String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaCityConfirmedVirusByTimestamp(province, timestamp);
    }

    @Override
    public List<VirusDensity> getChinaProvinceDensityVirusByTimestamp(int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaProvinceDensityVirusByTimestamp(timestamp);
    }

    @Override
    public List<VirusDensity> getChinaProvinceDensityVirusByTimestamp(String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaProvinceOnlyDensityVirusByTimestamp(province, timestamp);
    }

    @Override
    public List<VirusDensity> getChinaCityDensityVirusByTimestamp(String province, int timestamp) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaCityDensityVirusByTimestamp(province, timestamp);
    }
}
