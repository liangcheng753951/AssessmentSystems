package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.viewmodel.student.dashboard.IndexVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("StudentDashboardController")
@RequestMapping(value = "/api/student/dashboard")
public class DashboardController extends BaseApiController {

    @Autowired
    ExamPaperService examPaperService;


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();
        User user = getCurrentUser();
        indexVM.setFixedPaper(examPaperService.indexPaper(user));
        return RestResponse.ok(indexVM);
    }
}
