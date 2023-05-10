<!--这段代码实现了面包屑导航的功能。在模板中，使用了 el-breadcrumb 和 el-breadcrumb-item 组件来渲染面包屑导航，
    使用了 v-for 指令来遍历路由记录中的 levelList 数组，从而生成对应的面包屑导航项。
    在脚本部分，定义了一个名为 Breadcrumb 的 Vue 组件，使用了 path-to-regexp 库来实现动态路由的匹配，从而生成对应的面包屑导航项。
    在 created 钩子函数中调用了 getBreadcrumb 方法，实现初始化面包屑导航的数据。
    在 getBreadcrumb 方法中，通过 this.$route.matched 获取到当前路由的匹配记录，
    然后通过 filter 方法过滤掉不需要显示的记录，并将生成的 matched 数组赋值给 levelList，用于在模板中渲染面包屑导航。
    在 handleLink 方法中，处理面包屑导航项的点击事件，判断是否存在 redirect 字段，如果存在则跳转到对应的路由，
    否则调用 pathCompile 方法，将动态路由转换为实际的路径，并跳转到对应的路由。
-->
<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
        <span v-if="item.redirect==='noRedirect'||index==levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp'

export default {
  data() {
    return {
      levelList: null
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb() {
      // only show routes with meta.title
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
      const first = matched[0]

      if (!this.isDashboard(first)) {
        matched = [{ path: '/dashboard', meta: { title: '首页' }}].concat(matched)
      }

      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
    },
    pathCompile(path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      var toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink(item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(this.pathCompile(path))
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
