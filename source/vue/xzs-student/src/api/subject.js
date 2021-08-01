import { post } from '@/utils/request'

export default {
  list: query => post('/api/student/education/subject/list')
}
