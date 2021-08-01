package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ExamPaperAnswer implements Serializable {

    private static final long serialVersionUID = -2143539181805283910L;

    private Integer id;

    private Integer examPaperId;

    private String paperName;

    private Integer subjectId;

    private Integer systemScore;

    private Integer userScore;

    private Integer paperScore;

    private Integer questionCorrect;

    private Integer questionCount;

    private Integer doTime;

    private Integer status;

    private Integer createUser;

    private String createUsername;

    private Date createTime;
}
