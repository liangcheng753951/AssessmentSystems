package com.mindskip.xzs.viewmodel.student.exampaper;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageVM extends BasePage {

    private String name;

    private Integer subjectId;

    private Integer createUser;
}
