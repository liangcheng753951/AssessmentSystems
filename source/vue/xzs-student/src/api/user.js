import { post } from '@/utils/request'

export default {
  getCurrentUser: () => post('/api/student/user/current'),
  update: query => post('/api/student/user/update', query),
  updatePassword: query => post('/api/student/user/updatePassword', query)
}
