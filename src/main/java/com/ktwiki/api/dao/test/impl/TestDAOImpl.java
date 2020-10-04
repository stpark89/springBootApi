package com.ktwiki.api.dao.test.impl;

import com.ktwiki.api.dao.test.TestDAO;
import com.ktwiki.api.vo.TestVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class TestDAOImpl implements TestDAO {

    @Inject
    private SqlSession sqlSession;

    @Override
    public TestVo connectionTest() {
        return sqlSession.selectOne("test.connectionTest");
    }
}
