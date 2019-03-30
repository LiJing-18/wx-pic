// page/component/details/details.js
Page({
  data:{
    id:'',
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
    var that = this;
    wx.request({
      url: 'http://localhost:8080/photo/photo/selectById.action?id=' + this.data.id,
      //data: {
      //"id": this.data.id
      //},
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        console.log(res.data.data)
        that.setData({
          goods: res.data.data
        })
      }
    })
  },
 
  bindTap(e) {
    const index = parseInt(e.currentTarget.dataset.index);
    this.setData({
      curIndex: index
    })
  }
 
})