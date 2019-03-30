const qiniuUploader = require("../../../util/qiniuUploader.js");
//index.js

// 初始化七牛相关参数
function initQiniu() {
  wx.request({
    url: 'http://localhost:8080/photo/qiniu/getQiniuToken.action',
    method: 'POST',
    header: {
      'Content-Type': 'application/json'
    },
    success: function (res) {
      console.log(res);
      var options = {
        region: 'ECN', // 华北区
        //uptokenURL: '', //请求后端uptoken的url地址
        uptoken: res.data.data,  //你请求后端的uptoken,和上面一样的，uptokenURL不填就找uptoken,填一个就可以了（这里是字符串数据不是url了）
        domain: 'http://qiniu.in2off50.com', //yourBucketId:这个去你域名配置那里要
        shouldUseQiniuFileName: false,
        //key: '' 
      };
      console.log(options);
      qiniuUploader.init(options);
    }
  });
}

//获取应用实例
var app = getApp()
Page({
  data: {
    imageObject: {}
  },
  //事件处理函数
  onLoad: function () {
    console.log('onLoad')
    var that = this;
  },
  didPressChooesImage: function () {
    var that = this;
    didPressChooesImage(that);
  },
  didCancelTask: function () {
    this.data.cancelTask()
  },
  //表单提交
  formSubmit(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    wx.request({
      url: 'http://localhost:8080/photo/photo/uploadPhoto.action',
      data: {
        "name": e.detail.value.name,
        "url": e.detail.value.url,
        "other": e.detail.value.other,
        "createDate": e.detail.value.createDate
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == 200) {
          wx.showToast({
            title: '添加成功',
          })
          setTimeout(function () {
            wx.switchTab({
              url: '/page/component/category/category',
            })
          }, 2000)
        } else {
          wx.showToast({
            title: '添加失败',
          })
        }
      }
    })
  }
});

function didPressChooesImage(that) {
  initQiniu();
  // 微信 API 选文件
  wx.chooseImage({
    count: 1,
    success: function (res) {
      var filePath = res.tempFilePaths[0];
      // 交给七牛上传
      qiniuUploader.upload(filePath, (res) => {
        that.setData({
          'imageObject': res
        });
      }, (error) => {
        console.error('error: ' + JSON.stringify(error));
      },
        null,// 可以使用上述参数，或者使用 null 作为参数占位符
        (progress) => {
          console.log('上传进度', progress.progress)
          console.log('已经上传的数据长度', progress.totalBytesSent)
          console.log('预期需要上传的数据总长度', progress.totalBytesExpectedToSend)
        }, cancelTask => that.setData({ cancelTask })
      );
    }
  })
}