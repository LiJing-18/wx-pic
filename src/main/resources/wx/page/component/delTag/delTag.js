// page/component/new-pages/user/address/address.js
var app = getApp()
Page({
  data:{
    
  },
  formSubmit(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    wx.request({
      url: 'http://localhost:8080/photo/tag/del.action',
      data: {
        "name": e.detail.value.name
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == 200) {
          wx.showToast({
            title: '删除成功',
          })
          setTimeout(function () {
            wx.switchTab({
              url: '/page/component/category/category',
            })
          }, 2000)
        } else {
          wx.showToast({
            title: '删除失败',
          })
        }
      }
    })
  }
})