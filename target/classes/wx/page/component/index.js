Page({
  data: {
    category: [

    ],
    imgUrls: [
      '/image/b1.jpg',
      '/image/b2.jpg',
      '/image/b3.jpg'
    ],
    indicatorDots: false,
    autoplay: false,
    interval: 3000,
    duration: 800,
  },
  onReady() {
    var self = this;
    wx.request({
      url: 'http://10.0.1.183:8080/photo/photo/selectPhoto.action',
      success(res) {
        console.log(res.data)
        console.log(res.data.data)
        self.setData({
          category: res.data.data
        })
      }
    });
  }
})