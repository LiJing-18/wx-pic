Page({
    data: {
        category: [
            
        ],
        detail:[
          { id: '1', banner: '/image/A.png', cate: '演员', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'},
          { id: '1', banner: '/image/A.png', cate: '舞者', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'},
          { id: '1', banner: '/image/A.png', cate: '歌手', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'},
          { id: '1', banner: '/image/A.png', cate: '影后', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'},
          { id: '1', banner: '/image/A.png', cate: '网红', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'},
          { id: '1', banner: '/image/A.png', cate: '主播', name: 'guowei', thumb: '/image/c6.jpg', name: 'name'}
        ],
        curIndex: 0,
        isScroll: false,
        toView: 'guowei'
    },

    onReady(){
        var self = this;
        wx.request({
          url:'http://localhost:8080/photo/tag/tagSelectAll.action',
            success(res){
                console.log(res.data)
                console.log(res.data.data)
                self.setData({
                  category: res.data.data
                })
            }
        });
    }
    
})