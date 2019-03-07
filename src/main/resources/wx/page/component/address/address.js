// page/component/new-pages/user/address/address.js
var app = getApp()
Page({
  data:{
    
  },
  formSubmit(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    wx.request({
      url: 'http://10.0.1.183:8080/photo/tag/del.action',
      data: {
        "name": e.detail.value.name
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
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