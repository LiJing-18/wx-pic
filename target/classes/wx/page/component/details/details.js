// page/component/details/details.js
Page({
  data:{
    id: "",
    goods: {
      
    },
    hasCarts: false,
    curIndex: 0,
    show: false,
    scaleCart: false
  },
  onLoad: function (options) {
    this.setData({
      id: options.id
    });
    console.log('携带数据为：', this.data)
    wx.request({
      url: 'http://10.0.1.183:8080/photo/photo/selectById.action?id=' + this.data.id,
      //data: {
        //"id": this.data.id
      //},
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        this.data.goods=res.data
      }
    })
  }
})