package com.mindskip.xzs.viewmodel.admin.exam;

import com.mindskip.xzs.viewmodel.admin.question.QuestionEditRequestVM;
import lombok.Data;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ExamPaperTitleItemVM {

    @NotBlank(message = "Section name can not be null")
    private String name;

    @Size(min = 1,message = "please add questions")
    @Valid
    private List<QuestionEditRequestVM> questionItems;

}
