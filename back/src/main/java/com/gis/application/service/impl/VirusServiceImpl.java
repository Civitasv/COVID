package com.gis.application.service.impl;

import com.gis.application.mapper.VirusMapper;
import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public List<HashMap<String, Object>> getVirusByTime(int publicTime) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getVirusByTime(publicTime);
    }

    @Override
    public List<HashMap<String, Object>> getAllVirus() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getAllVirus();
    }

    @Override
    public List<HashMap<String, Object>> getAllChinaVirus() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getAllChinaVirus();
    }

    @Override
    public int updateVirusByCountry(String country,double lng,double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateVirusByCountry(country,lng,lat);
    }

    @Override
    public int updateVirusByProvince(String province, double lng, double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateVirusByProvince(province,lng,lat);
    }

    @Override
    public int updateVirusByID(int id, double lng, double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateVirusByID(id,lng,lat);
    }

    @Override
    public List<HashMap<String, Object>> selectLatLngEqualZero() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.selectLatLngEqualZero();
    }

    @Override
    public int updateID(int id,int ids) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateID(id,ids);
    }

    @Override
    public List<HashMap<String, Object>> getVirusDivideByCountry() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getVirusDivideByCountry();
    }

    @Override
    public List<HashMap<String, Integer>> getVirusDivideByTime() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getVirusDivideByTime();
    }

    @Override
    public List<HashMap<String, Integer>> getChinaVirusDivideByTime() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getChinaVirusDivideByTime();
    }

    @Override
    public List<HashMap<String, Object>> getProvinceData() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getProvinceData();
    }
}
