package com.ktwiki.api.vo;

import lombok.Data;

/**
 * KT - 인증서버에서 넘어오는 로그인 정보
 *    - 로그인한 정보를 받으면 파싱해서 OauthVo 객체를 생성.
 */
@Data
public class OauthUser {

    private String ssoLoginToken;

}
