import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Vuex from 'vuex';
Vue.use(Vuex);

/* Layout */
import Layout from '@/layout'
import store from '@/store'



/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/MyLogin.vue'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard', affix: true }
    }]
  },

  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/user',
    name: 'sysManage',
    meta: { title: '人员信息管理', icon: 'sys2' },
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/sys/user'),
        meta: { title: '教务管理员', icon: 'user' , permission: ['user']}
      },
      {
        path: 'teacher',
        name: 'teacher',
        component: () => import('@/views/sys/teacher.vue'),
        meta: { title: '院系老师', icon: 'role' , permission: ['user', 'teacher', 'student']}
      },
      {
        path: 'student',
        name: 'student',
        component: () => import('@/views/sys/student.vue'),
        meta: { title: '在校学生', icon: 'role' , permission: ['user', 'teacher']}
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'course',
        name: 'course',
        component: () => import('@/views/course/index.vue'),
        meta: { title: '全校开课情况', icon: 'test' }
      }
    ]

  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'mycourse',
        name: 'mycourse',
        component: () => import('@/views/course/myCourse.vue'),
        meta: { title: '我开设的课程', icon: 'sys', permission: ['teacher'] }
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    children: [
      // 只能由教务管理员添加
      {
        path: 'health',
        name: 'health',
        component: () => import('@/views/health/index'),
        meta: { title: '学生体检情况', icon: 'sys' , permission: ['user'] }

      }
    ]
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'score',
        name: 'score',
        component: () => import('@/views/score/index.vue'),
        meta: { title: '全校学生成绩情况', icon: 'sys', permission: ['user'] }
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'myhealth',
        name: 'myhealth',
        component: () => import('@/views/health/myhealth.vue'),
        meta: { title: '我的体检报告', icon: 'sys', permission: ['student'] }
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'myscore',
        name: 'myscore',
        component: () => import('@/views/score/myScore.vue'),
        meta: { title: '我的成绩单', icon: 'sys', permission: ['student'] }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: '表单模版', icon: 'sys' }
      }
    ]
  },

  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: 'Nested',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Menu1',
  //       meta: { title: 'Menu1' },
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: () => import('@/views/nested/menu1/menu1-1'),
  //           name: 'Menu1-1',
  //           meta: { title: 'Menu1-1' }
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Menu1-2',
  //           meta: { title: 'Menu1-2' },
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //               name: 'Menu1-2-1',
  //               meta: { title: 'Menu1-2-1' }
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //               name: 'Menu1-2-2',
  //               meta: { title: 'Menu1-2-2' }
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'Menu1-3',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://www.bb.ustc.edu.cn/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

// router.beforeEach((to, from, next) =>{
//   const requiredPermission = to.meta.permission;
//   const userPermission = store.state.user.roleType;
//
//   if (requiredPermission && !requiredPermission.includes(userPermission)) {
//     // 没有权限访问该路由, 将其隐藏
//
//     next(false);
//   } else {
//     // 有权限访问该路由，继续正常导航
//     next();
//   }
// })

export default router
