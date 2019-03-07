// page/component/tag/tag.js
var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {

  },
  //表单提交
  formSubmit(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    wx.request({
      url: 'http://10.0.1.183:8080/photo/tag/insert.action',
      data: {
        "name": e.detail.value.name,
        "weigth": e.detail.value.weigth,
        "state": e.detail.value.state,
        "createDate": e.detail.value.createDate
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        wx.redirectTo({
          url: '../category/category'// 希望跳转过去的页面
        })
      }
    })
  }
})