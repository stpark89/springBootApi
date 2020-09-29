package com.ktwiki.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * * 로그인 서버에서 넘어온 정보를 바탕으로 만든 Oauth 객체를 기준으로
 * * 해당 NautilusUser 객체에 매핑해줘야함.
 *
 * * 실제 시큐리티 내부에서 사용되는 Vo 임
 *  1depth - NautilusUser
 *    2depth - NotilusRole
 *      3depth - NotilusAuthority
 *
 */
public class NautilusUser implements Serializable {


    private UUID id;

    private String name;

    private boolean enabled;

    private List<NautilusRole> roles = new ArrayList<>();

}
