package com.ktwiki.api.service.test;

import com.ktwiki.api.dao.test.TestDAO;
import com.ktwiki.api.vo.TestVo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Log
@Service
public class TestService {

    @Autowired
    private TestDAO testDAO;

    public TestVo connectionTest() {
        log.info("connectionTest");
        return testDAO.connectionTest();
    }
}
