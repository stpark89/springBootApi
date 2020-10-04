package com.ktwiki.api.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiResponseVo<T> {

    @ApiModelProperty(value="Http Status")
    private String status;

    @ApiModelProperty(value="메세지")
    private String message;

    @ApiModelProperty(value="error Message")
    private String errorMessage;

    @ApiModelProperty(value="현재 시간")
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @ApiModelProperty(value="json  데이터")
    private T response;

}
