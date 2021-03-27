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
    public List<VirusNewIncrease> getWorldNewIncreaseVirusData() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getWorldNewIncreaseVirusData();
    }

    @Override
    public List<VirusConfirmedRecovered> getConfirmedRecoveredVirusData() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getConfirmedRecoveredVirusData();
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
}
