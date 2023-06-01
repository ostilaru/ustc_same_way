const Mock = require('mockjs')

// 这里使用 Mock.js 的 Mock.mock 方法创建了一个模拟数据对象 data。该对象中有一个 items 属性，它的值是一个包含 30 个对象的数组。
// 每个对象都有 id、title、status、author、display_time、pageviews 这几个属性，
// 其中 id 是随机生成的，title 是随机生成的一句话，status 是随机生成的文章状态，author 是固定的名字，display_time 是随机生成的时间，pageviews 是随机生成的一个数字。
const data = Mock.mock({
  'items|30': [{
    id: '@id',
    title: '@sentence(10, 20)',
    'status|1': ['published', 'draft', 'deleted'],
    author: 'name',
    display_time: '@datetime',
    pageviews: '@integer(300, 5000)'
  }]
})

// 这段代码用于模拟一个接口，返回的数据是随机生成的，可以用于测试
module.exports = [
  {
    url: '/vue-admin-template/table/list',
    type: 'get',
    response: config => {
      const items = data.items
      return {
        code: 20000,
        data: {
          total: items.length,
          items: items
        }
      }
    }
  }
]
