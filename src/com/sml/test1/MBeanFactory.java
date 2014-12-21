package com.sml.test1;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.NamingException;

import com.sun.jndi.url.rmi.rmiURLContextFactory;


/**
 * MBean工厂
 * 
 * @author ttwang
 * 
 */
public class MBeanFactory {

	private String URL = "service:jmx:rmi:///jndi/rmi://";

	private String serverName = "/karaf-root";

	private String USERNAME = "karaf";

	private String PASSWORD = "karaf";

	/**
	 * 获取服务列表
	 * 
	 * @param ip
	 * @param port
	 * @return
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public List<ObjectName> getServiceList(String ip, String port)
			throws IOException, MalformedObjectNameException,
			NullPointerException {
		String serverUrl = URL + ip + ":" + port + serverName;
		MBeanServerConnection mbeanServerConnection = null;
		JMXConnector jmxConnector = null;
		JMXServiceURL serviceURL = new JMXServiceURL(serverUrl);
		Map<String, String[]> environment = new HashMap<String, String[]>();
		String[] credentials = new String[] { USERNAME, PASSWORD };
		environment.put("jmx.remote.credentials", credentials);
		jmxConnector = JMXConnectorFactory.connect(serviceURL, environment);
		mbeanServerConnection = jmxConnector.getMBeanServerConnection();
		Set<ObjectInstance> mbeans = mbeanServerConnection.queryMBeans(
				new ObjectName("com.travelsky.jcf:type=service,name=*"), null);
		List<ObjectName> services = new ArrayList<ObjectName>();
		if (mbeans != null && mbeans.size() > 0) {
			Iterator<ObjectInstance> it = mbeans.iterator();
			while (it.hasNext()) {
				ObjectInstance oi = it.next();
				services.add(oi.getObjectName());
			}
		}
		return services;
	}

	/**
	 * 服务对象
	 * 
	 * @param ip
	 * @param port
	 * @param serviceName
	 *            服务名
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws MalformedObjectNameException
	 */
	
	/**
	 * 节点控制器
	 * 
	 * @param port
	 *            端口
	 * @param ip
	 * 
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws MalformedObjectNameException
	 */
	public ServerControllerMBean getServerController(String ip, String port)
			throws MalformedObjectNameException, IllegalArgumentException,
			NullPointerException {
		String serverUrl = URL + ip + ":" + port + "/nodeController";
		String objectName = "com.travelsky.jcf:type=ServerController,name=nodeControllerMBean";
		return getMBeanProxy(serverUrl, objectName, ServerControllerMBean.class);
	}



	/**
	 * 反射获取mbean
	 * 
	 * @param serverUrl
	 * @param objectName
	 * @param proxyInterface
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	@SuppressWarnings("unchecked")
	private <T> T getMBeanProxy(String serverUrl, String objectName,
			Class<T> proxyInterface) throws MalformedObjectNameException,
			IllegalArgumentException, NullPointerException {
		return (T) Proxy.newProxyInstance(MBeanFactory.class.getClassLoader(),
				new Class<?>[] { proxyInterface }, new MBeanWapperHandler(
						serverUrl, objectName, proxyInterface));
	}

	class MBeanWapperHandler implements InvocationHandler {
		private String serverUrl;

		private ObjectName objectName;

		private Class proxyInterface;

		MBeanWapperHandler(String url, String oName, Class proxyInterface)
				throws MalformedObjectNameException, NullPointerException {
			serverUrl = url;
			this.objectName = new ObjectName(oName);
			this.proxyInterface = proxyInterface;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			Object o = null;
			JMXConnector connector = null;
			try {
				JMXServiceURL jmxUrl = new JMXServiceURL(serverUrl);
				Map<String, Object> environment = new HashMap<String, Object>();
				String[] credentials = new String[] { USERNAME, PASSWORD };
				environment.put("jmx.remote.credentials", credentials);
				environment.put(
						Context.INITIAL_CONTEXT_FACTORY,
						SunRmiURLContextFactory.class.getName().replace('$',
								'.'));
				connector = JMXConnectorFactory.connect(jmxUrl, environment);
				MBeanServerConnection mbsc = connector
						.getMBeanServerConnection();
				Object obj = MBeanServerInvocationHandler.newProxyInstance(
						mbsc, objectName, proxyInterface, true);
				o = method.invoke(obj, args);
			} catch (Exception e) {
				// e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if (connector != null) {
					try {
						connector.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			return o;
		}
	}

}



