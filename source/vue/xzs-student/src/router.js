import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)
const router = new Router({
  routes: [
    { path: '/login', name: 'Login', component: () => import('@/views/login/index'), meta: { title: 'Login', bodyBackground: '#fbfbfb' } },
    { path: '/register', name: 'Register', component: () => import('@/views/register/index'), meta: { title: 'Register', bodyBackground: '#fbfbfb' } },
    {
      path: '/',
      component: Layout,
      redirect: '/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/dashboard/index'),
          name: 'Dashboard',
          meta: { title: 'Main' }
        }
      ]
    },
    {
      path: '/paper',
      component: Layout,
      children: [
        {
          path: 'index',
          component: () => import('@/views/paper/index'),
          name: 'PaperIndex',
          meta: { title: 'Paper' }
        }
      ]
    },
    {
      path: '/record',
      component: Layout,
      children: [
        {
          path: 'index',
          component: () => import('@/views/record/index'),
          name: 'RecordIndex',
          meta: { title: 'Assessment Record' }
        }
      ]
    },
    {
      path: '/user',
      component: Layout,
      children: [
        {
          path: 'index',
          component: () => import('@/views/user-info/index'),
          name: 'UserInfo',
          meta: { title: 'Profile' }
        }
      ]
    },
    { path: '/do', name: 'ExamPaperDo', component: () => import('@/views/exam/paper/do'), meta: { title: 'Assessment' } },
    { path: '/read', name: 'ExamPaperRead', component: () => import('@/views/exam/paper/read'), meta: { title: 'Paper result' } },
    { path: '*', component: () => import('@/views/error-page/404'), meta: { title: '404' }
    }
  ]
})

export { router }
