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
        detail:[],
        curIndex: 0,
        isScroll: false,
        toView: 'guowei'
    },
    onReady(){
        var self = this;
        wx.request({
            url:'http://www.gdfengshuo.com/api/wx/cate-detail.txt',
            success(res){
                console.log(res.data)
                self.setData({
                    detail : res.data.result
                })
            }
        });
        
    },
    switchTab(e){
        this.setData({
            toView : e.target.dataset.id,
            curIndex : e.target.dataset.index
        })
    }
    
})