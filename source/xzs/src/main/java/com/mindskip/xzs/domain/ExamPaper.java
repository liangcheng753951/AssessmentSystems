package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    private Integer id;

    private String name;

    private Integer subjectId;

    private Integer score;

    private Integer questionCount;

    private Integer suggestTime;

    private Integer frameTextContentId;

    private Integer createUser;

    private Date createTime;

    private Boolean deleted;
}
