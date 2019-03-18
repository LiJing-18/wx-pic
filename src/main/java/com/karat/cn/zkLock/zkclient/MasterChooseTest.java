package com.karat.cn.zkLock.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * master选举测试
 */
public class MasterChooseTest {

	private final static String CONNECTSTRING="47.107.121.215:2181";


    public static void main(String[] args) throws IOException {
        List<MasterSelector> selectorLists=new ArrayList<>();
        try {
            for(int i=0;i<10;i++) {
                ZkClient zkClient = new ZkClient(CONNECTSTRING, 5000,
                        5000,
                        new SerializableSerializer());
                UserCenter userCenter = new UserCenter();
                userCenter.setId(i);
                userCenter.setName("客户端：" + i);

                MasterSelector selector = new MasterSelector(userCenter,zkClient);
                selectorLists.add(selector);
                selector.start();//触发选举操作
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for(MasterSelector selector:selectorLists){
                selector.stop();
            }
        }
    }
}
