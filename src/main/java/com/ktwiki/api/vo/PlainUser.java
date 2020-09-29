package com.ktwiki.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * id , pw
 */

@Data
public class PlainUser implements Serializable{

    private String id;

    private Password password;

    private NautilusUser nautilusUser;

}
