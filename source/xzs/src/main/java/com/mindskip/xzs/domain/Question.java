package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 8826266720383164363L;

    private Integer id;

    private Integer subjectId;

    private Integer score;

    private Integer difficult;

    private String keyPoints;

    private String correct;

    private Integer infoTextContentId;

    private Integer createUser;

    private Date createTime;

    private Boolean deleted;
}
