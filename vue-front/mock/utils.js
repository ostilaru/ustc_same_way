/**
 * @param {string} url
 * @returns {Object}
 */

// 这段代码用于将url中的参数转换为对象
function param2Obj(url) {
  // 首先通过 url.split('?') 将 URL 按问号拆分成两部分, 取第二部分即查询字符串部分。然后使用 decodeURIComponent 对查询字符串进行解码，并使用 replace(/\+/g, ' ') 将加号替换为空格。
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  if (!search) {
    return {}
  }
  const obj = {}
  const searchArr = search.split('&')
  searchArr.forEach(v => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index)
      const val = v.substring(index + 1, v.length)
      obj[name] = val
    }
  })
  return obj
}

module.exports = {
  param2Obj
}
