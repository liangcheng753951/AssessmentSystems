package com.mindskip.xzs.viewmodel.admin.paper;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer subjectId;

    private String paperName;

    private String username;

}
