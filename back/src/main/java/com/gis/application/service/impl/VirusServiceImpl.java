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
    public List<HashMap<String, Object>> getVirusByAddressAndTime(int publicTime,String address) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getVirusByAddressAndTime(publicTime,address);
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
    public int updateLngLatByCountry(String country,double lng,double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateLngLatByCountry(country,lng,lat);
    }

    @Override
    public int updateLngLatByProvince(String province, double lng, double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateLngLatByProvince(province,lng,lat);
    }

    @Override
    public int updateLngLatByID(int id, double lng, double lat) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateLngLatByID(id,lng,lat);
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
        return mapper.addImage(id,base64);
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
