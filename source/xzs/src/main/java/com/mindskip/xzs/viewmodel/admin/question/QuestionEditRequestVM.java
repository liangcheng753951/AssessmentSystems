package com.mindskip.xzs.viewmodel.admin.question;


import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class QuestionEditRequestVM {

    private Integer id;
    @NotNull
    private Integer subjectId;
    @NotBlank
    private String title;

    private List<String> keyPoints;

    @Valid
    private List<QuestionEditItemVM> items;
    @NotBlank
    private String analyze;

    @NotBlank
    private String correct;
    @NotBlank
    private String score;

    @Range(min = 1, max = 5, message = "please check the difficulty")
    private Integer difficult;

    private Integer itemOrder;
}
