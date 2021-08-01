import { post } from '@/utils/request'

export default {
  selectForAssessment: id => post('/api/student/exam/paper/selectForAssessment/' + id),
  pageList: query => post('/api/student/exam/paper/pageList', query)
}
