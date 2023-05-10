<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  // render是Vue中的渲染函数，它是一个纯JavaScript函数，用于描述组件的结构和行为。使用render函数可以动态地构建组件，而无需使用模板语法。
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      if (icon.includes('el-icon')) {
        vnodes.push(<i class={[icon, 'sub-el-icon']} />)
      } else {
        vnodes.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    // 在Vue中，slot（插槽）是一种用于在父组件中传递子组件内容的技术。使用slot可以将子组件的内容插入到父组件的指定位置，从而实现父子组件之间的灵活组合。
    // 具体来说，当父组件包含子组件的<slot>标签时，子组件可以将自己的内容插入到该标签中，作为父组件的一部分
    }
    return vnodes
  }
}
</script>

<style scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>
