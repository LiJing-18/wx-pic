Page({
    data: {
        category: [
            {name:'演员',id:'guowei'},
            {name:'舞者',id:'shucai'},
            {name:'歌手',id:'chaohuo'},
            {name:'影后',id:'dianxin'},
            {name:'网红',id:'cucha'},
            {name:'主播',id:'danfan'}
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
        //wx.request({
            //url:'http://www.gdfengshuo.com/api/wx/cate-detail.txt',
            //success(res){
                //console.log(res.data)
                //self.setData({
                   // detail : res.data.result
                //})
            //}
        //});
        
    },
    switchTab(e){
        this.setData({
            toView : e.target.dataset.id,
            curIndex : e.target.dataset.index
        })
    }
    
})