package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.ExamPaperAnswer;
import com.mindskip.xzs.domain.ExamPaperAnswerInfo;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperSubmitVM;
import com.mindskip.xzs.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperAnswerService extends BaseService<ExamPaperAnswer> {

    PageInfo<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM requestVM);

    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user);

    ExamPaperSubmitVM examPaperAnswerToVM(Integer id);

    Integer countByStatusAndStudentAndPaper(User user, Integer paperId, Integer status);

    PageInfo<ExamPaperAnswer> adminPage(com.mindskip.xzs.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM requestVM);
}
