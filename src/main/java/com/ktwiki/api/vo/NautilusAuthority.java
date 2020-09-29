package com.ktwiki.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NautilusAuthority implements Serializable {

    private String name;

    private String comment;

}
