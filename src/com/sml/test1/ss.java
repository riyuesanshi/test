package com.sml.test1;

import java.util.HashMap;
import java.util.Map;

import javax.management.MalformedObjectNameException;
import javax.print.attribute.HashAttributeSet;

public class ss {
	public static void main(String[] args) {
		
		Map map = new HashMap<String, String>();
		map.put("mqPort", "444");
		map.put("loglevel", "ERROR");
		map.put("mqManager", "TeST");
		map.put("ccsid", "3444");
		map.put("queuecapacity", "1333");
		map.put("mqName", "RRRR");
		map.put("mqHostIP", "13.2.3.2");
		
		try {
			ServerControllerMBean serverControllerMBean = (new MBeanFactory()).getServerController("10.6.148.223","8888");
			serverControllerMBean.setNodeControllerConfig(map);
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
