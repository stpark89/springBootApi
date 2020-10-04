package com.ktwiki.api.controller;

import com.ktwiki.api.service.test.TestService;
import com.ktwiki.api.vo.TestVo;
import com.ktwiki.api.vo.common.ApiResponseVo;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Rest API 사용시
 * GET, POST, PUT, DELETE  - 조회, 등록, 수정, 삭제
 * @RequestBody : 클라이언트로부터 넘어오는 JSON 데이터 받을 경우
 * @OathVariable : URL 경로에 포함된 정보 추출
 */
@Log
@RequestMapping(value="/api")
@RestController
public class RestDataController {

    @Autowired
    private TestService testService;

    /**
     * Get 요청
     * @return
     */
    @GetMapping(value="/restTest")
    @ApiOperation(value = "Test Sample", tags = "sample")
    public ResponseEntity<ApiResponseVo> restTest(){
        log.info("restTest");
        TestVo test = new TestVo();
        test.setMessage("첫번째 메세지");
        test.setName("홍길동");

        ApiResponseVo vo = new ApiResponseVo();
        vo.setMessage("SUCCESS");
        vo.setResponse(test);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @GetMapping(value="/restListTest")
    @ApiOperation(value = "Test List Sample", httpMethod="GET", tags = "sample List")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 200, message = "Ok" )
    })
    public ResponseEntity<ApiResponseVo> restListTest(){
        log.info("restListTest");
        List<TestVo> returnList = new ArrayList<>();
        TestVo test = new TestVo();
        test.setMessage("첫번째 메세지");
        test.setName("홍길동");
        returnList.add(test);

        ApiResponseVo vo = new ApiResponseVo();
        vo.setMessage("SUCCESS");
        vo.setResponse(returnList);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    /**
     * Get 요청
     * @return
     */
    @GetMapping(value="/errorTest")
    public String errorTest(@RequestBody Date id) throws Exception {
        log.info("errorTest");
        System.out.println("아이디 확인중 -----");
        System.out.println(id);
        return "Test";
    }

    /**
     * 디비 정보 조회
     */
    @GetMapping(value="/connectionTest")
    public TestVo connectionTest() throws Exception{
        log.info("connection Test");
        return testService.connectionTest();
    }

}
