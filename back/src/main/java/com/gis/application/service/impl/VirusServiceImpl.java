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
    public int insertHistoryVirus(Virus virus) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.insertHistoryVirus(virus);
    }

    @Override
    public int insertRecentVirus(Virus virus) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.insertRecentVirus(virus);
    }

    @Override
    public int updateVirusOriginByID(String origin, int id) {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.updateVirusOriginByID(origin,id);
    }

    @Override
    public List<HashMap<String, Object>> getAllOrigin() {
        VirusMapper mapper = sqlSession.getMapper(VirusMapper.class);
        return mapper.getAllOrigin();
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
}
