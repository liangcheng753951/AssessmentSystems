package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExamPaperQuestionCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 3389482731220342366L;

    private Integer id;

    private Integer questionId;

    private Integer examPaperId;

    private Integer examPaperAnswerId;

    private Integer subjectId;

    private Integer customerScore;

    private Integer questionScore;

    private Integer questionTextContentId;

    private String answer;

    private Integer textContentId;

    private Boolean doRight;

    private Integer createUser;

    private Date createTime;

    private Integer itemOrder;
}
