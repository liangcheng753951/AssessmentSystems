package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Subject implements Serializable {

    private static final long serialVersionUID = 8058095034457106501L;

    private Integer id;

    private String name;

    private Integer itemOrder;

    private Boolean deleted;
}