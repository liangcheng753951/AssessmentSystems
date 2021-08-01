package com.mindskip.xzs.viewmodel.admin.exam;



import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ExamPaperEditRequestVM {
    private Integer id;

    @NotNull
    private Integer subjectId;
    @NotBlank
    private String name;
    @NotNull
    private Integer suggestTime;

    @Size(min = 1,message = "please add paper sections")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    private String score;

    private boolean duplicateWork = false;
}
