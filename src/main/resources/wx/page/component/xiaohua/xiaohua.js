// pages/homePage/homePage.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showImages: [],
    scrHeight: '',
    cliHeight: '',
    dataArray: [],
    verify: 0,
    showRegisterReward: false,
    showRegisterRemind: false,
    usergold: 0
  },
  // 审核开关
  ajaxVerify: function () {
    var self = this;
    app.network.loadData('Userapi/auditstatus', {}, '')
      .then(res => {
        self.setData({
          verify: res.data.status
        })
      })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.logintype == 2) {
      this.setData({
        showRegisterRemind: true
      })
    } else if (options.logintype == 3) {
      this.setData({
        showRegisterReward: true
      })
    }
    if (app.globalData.nickid == null || app.globalData.nickid == undefined) {
      var voMemberData = wx.getStorageSync('voMemberData');
      app.globalData.nickid = voMemberData.nickid;
    }
    this.excellentbookman();
    this.ajaxVerify();
  },
  closeRegister: function () {
    this.setData({
      showRegisterRemind: false
    })
  },
  hiddenRemind: function () {
    this.setData({
      showRegisterReward: false
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var self = this;
    let cliHeight = wx.getSystemInfoSync().windowHeight;
    wx.createSelectorQuery().select('#header').boundingClientRect(function (rect) {
      let scrHeight = cliHeight - rect.bottom;
      self.setData({
        scrHeight: scrHeight + 'px'
      })
    }).exec()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onTabItemTap: function () {
    this.excellentbookman();
  },
  onShow: function () {
    if (app.globalData.nickid) {
      this.catusergold();
    }
  },
  // 跳转至查看全部漫主
  gotoAllAuthor: function (e) {
    wx.navigateTo({
      url: '../allAuthor/allAuthor',
    })
  },
  // 查看漫币数量
  catusergold: function (e) {
    var self = this;
    let params = {
      nickid: app.globalData.nickid
    }
    app.network.loadData('Userapi/catusergold', params, "")
      .then(res => {
        if (res.data.gold) {
          self.setData({
            usergold: res.data.gold
          })
        }
      })

  },
  // 查看优秀漫主
  excellentbookman: function () {
    var self = this;
    let params = {
      nickid: app.globalData.nickid || ''
    }
    app.network.loadData('Userapi/excellentbookman', params, "加载中")
      .then(res => {
        if (res.data.msg) {
          self.setData({
            dataArray: res.data.msg
          })
        }
      })

  },
  // 充值
  chongzhi: function () {
    wx.navigateTo({
      url: '../commonintroduce/commonintroduce?pagetype=0',
    })
  },
  // 跳转到他人主页
  personalPage: function (e) {
    if (e.detail.formId != 'the formId is a mock one') {
      let form = {
        nickid: app.globalData.nickid,
        formid: e.detail.formId
      }
      app.network.loadData('Userapi/formopen', form, '')
        .then(res => { })
    }
    if (app.globalData.nickid == e.currentTarget.dataset.nickid) {
      wx.switchTab({
        url: '../personalPage/personalPage',
      })
    } else {
      wx.navigateTo({
        url: '../otherPersonalPage/otherPersonalPage?nickid=' + e.currentTarget.dataset.nickid,
      })
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    var shareObj = {};
    if (!app.globalData.shareData) {
      app.globalData.shareData = wx.getStorageSync('shareData');
    }
    app.globalData.shareData.forEach(function (item, index) {
      if (item.type == 3) {
        shareObj = item;
      }
    })
    return {
      title: shareObj.content,
      path: 'pages/homePage/homePage',
      imageUrl: shareObj.conimg,
      success: function (res) {

      }
    }
  }
})