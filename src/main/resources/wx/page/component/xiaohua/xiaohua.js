//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    duanzi: []
  },
  onShow: function () {
    wx.showToast({
      title: '加载中',
      icon: 'loading',
      duration: 500
    })
    setTimeout(function () {
      wx.hideToast()
    }, 500);
  },
  onLoad: function () {
    var that = this;
    wx.request({
      url: 'http://localhost:8080/photo/duanzi/duanZiSelectAll.action',
      success: function (res) {
        console.log(res.data.list);
        that.setData({
          duanzi: res.data.list,
        })
      }
    })
  },
  /**
    * 页面相关事件处理函数--监听用户下拉动作
    */
  onPullDownRefresh: function () {

  },
  /**
* 页面上拉触底事件的处理函数
*/
  onReachBottom: function () {

  },

})
