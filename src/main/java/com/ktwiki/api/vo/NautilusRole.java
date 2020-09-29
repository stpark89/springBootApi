package com.ktwiki.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  시큐리티 에서 사용하는 유저 객체
 *  NautilusUser 내부에서 사용하는 역할Vo
 */
@Data
public class NautilusRole implements Serializable {

    private String name;

    private String comment;

    private List<NautilusAuthority> authorities = new ArrayList<>();

}
