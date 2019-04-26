package com.karat.cn.util.time;

import java.util.UUID;

public class IdUtil {
	
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String uuidStr=uuid.toString();
        return uuidStr;
	}
}
