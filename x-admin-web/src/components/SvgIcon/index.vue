<!--这段代码定义了一个名为 "SvgIcon" 的组件，用于展示 SVG 图标。
    该组件接受两个 props，一个是图标类名 "iconClass"，另一个是组件的 class 名称 "className"，
    并通过计算属性 "iconName" 和 "svgClass" 分别计算出图标名称和 SVG 样式类名。
    根据 "isExternal" 计算属性的值来判断是否为外部链接图标，并使用不同的样式渲染。
    如果是外部链接图标，则使用 CSS mask 属性来设置背景图像。最后，将该组件导出供其他组件使用。
-->
<template>
  <div v-if="isExternal" :style="styleExternalIcon" class="svg-external-icon svg-icon" v-on="$listeners" />
  <svg v-else :class="svgClass" aria-hidden="true" v-on="$listeners">
    <use :xlink:href="iconName" />
  </svg>
</template>

<script>
// doc: https://panjiachen.github.io/vue-element-admin-site/feature/component/svg-icon.html#usage
import { isExternal } from '@/utils/validate'

export default {
  name: 'SvgIcon',
  props: {
    iconClass: {
      type: String,
      required: true
    },
    className: {
      type: String,
      default: ''
    }
  },
  computed: {
    isExternal() {
      return isExternal(this.iconClass)
    },
    iconName() {
      return `#icon-${this.iconClass}`
    },
    svgClass() {
      if (this.className) {
        return 'svg-icon ' + this.className
      } else {
        return 'svg-icon'
      }
    },
    styleExternalIcon() {
      return {
        mask: `url(${this.iconClass}) no-repeat 50% 50%`,
        '-webkit-mask': `url(${this.iconClass}) no-repeat 50% 50%`
      }
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

.svg-external-icon {
  background-color: currentColor;
  mask-size: cover!important;
  display: inline-block;
}
</style>
