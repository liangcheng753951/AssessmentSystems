package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.ExamPaperQuestionCustomerAnswer;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperSubmitItemVM;

import java.util.List;

public interface ExamPaperQuestionCustomerAnswerService extends BaseService<ExamPaperQuestionCustomerAnswer> {

    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa);

}
