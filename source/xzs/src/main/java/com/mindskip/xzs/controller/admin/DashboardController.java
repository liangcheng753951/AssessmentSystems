package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.service.QuestionService;
import com.mindskip.xzs.viewmodel.admin.dashboard.IndexVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
public class DashboardController extends BaseApiController {

    private final ExamPaperService examPaperService;
    private final QuestionService questionService;

    @Autowired
    public DashboardController(ExamPaperService examPaperService, QuestionService questionService) {
        this.examPaperService = examPaperService;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();
        Integer examPaperCount = examPaperService.selectAllCount();
        Integer questionCount = questionService.selectAllCount();
        vm.setExamPaperCount(examPaperCount);
        vm.setQuestionCount(questionCount);
        return RestResponse.ok(vm);
    }
}
