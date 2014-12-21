package com.sml.test1;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface ServerControllerMBean {

	public static final String STOPPED = "Stopped";

	public final static String STARTING = "Starting";

	public final static String RUNNING = "Started";

	public final static String STOPPING = "Stopping";
	
	public final static String SERVEREXCEPTION = "ServerException";
	
	public final static String JMXEXCEPTION = "JMXException";
	
	public final static String SERVERDOWN = "ServerDown";

	public final static String JCF_SERVER = "JCF";

	public final static String REGISTRY_SERVER = "REGISTRY";

	public final static String MEMCACHE_SERVER = "MEMCACHE";
	
	public final static String JCF_SERVER_WS = "JCF-WS";

	public final static String JCF = "1";

	public final static String REGISTRY = "4";

	public final static String MEMCACHE = "3";

	public final static String NODECONTROLLER = "5";

	public static final String JCFSERVERCONTEXTCONFIGPATH = "/jcf-etc/jcfmemcache.properties";
	public static final String JCFETC = "/jcf-etc/";
	public static final String JCFSERVERCONTEXTCONFIGNAME = "jcfmemcache.properties";
	public static final String JCFSERVERDATASOURCECONCIFGPATH = "/datasource";
	public static final String JCFSERVERAPPCONFIGPATH = "/app-config";
	/**
	 * 初始化server信息
	 * 
	 * @throws IOException
	 */
	public void init() throws Exception;

	/**
	 * 根据serverId获得server状态
	 * 
	 * @param serverId
	 * @return Starting, Started, Stopping, Stopped
	 */
	public String serverStatus(String serverId) throws IOException;

	/**
	 * 启动服务器
	 * 
	 * @param serverId
	 *            server标识
	 * @throws Exception
	 */
	public void startServer(String serverId) throws NodeControllerException,IOException;

	/**
	 * 停止服务器
	 * 
	 * @param serverId
	 *            serverId标识
	 * @param force
	 *            是否强制停止 true:强制 false:非强制
	 * @throws Exception
	 */
	public void stopServer(String serverId, boolean force) throws NodeControllerException,IOException;

	/**
	 * 添加server
	 * 
	 * @param serverId
	 *            -server标识| serverType-server类型 JCF_SERVER：jcf/memcache|
	 *            serverPath-server路径| jmxPath-jmx url| retry-是否实时监测 true、false|
	 *            retryTime-重启次数
	 */
	// public void addServer(Map<String, String> map);

	/**
	 * 添加server
	 * 
	 * @param serverPath
	 *            服务器地址
	 */
	public void addServer(String serverPath) throws Exception;

	/**
	 * 卸载服务器
	 * 
	 * @param serverPath
	 */
	public void unInstallServer(String serverId);

	/**
	 * 监控server
	 */
	public void monitorServer();

	/**
	 * 获取服务库参数配置信息
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getServerParameters(String serverId) throws Exception;

	/**
	 * 修改服务库参数配置信息
	 * 
	 * @param parameters
	 *            需要修改的参数集合
	 */
	public boolean setServerParameters(String serverId, Map<String, String> parameters)
			throws Exception;


	public void deleteServer(String serverName) throws Exception;

	public void addServer(String serverName, String serverType,
			Map<String, byte[]> parasMap, boolean autoStart,String sameGroupOldServerName) throws Exception;

	public void updateServer(String serverName, Map<String, byte[]> parasMap,
			boolean autoStart) throws Exception;


	String getServerPath(String serverName);

	boolean setApplogPath(String serverName, String appName);

	String getApplogPath(String serverName, String appName);

	String getServerlogPath(String serverName);

	void coverConfig(String path, byte[] b, String serverName);

	Map<String, byte[]> getDsContext(String serverName);

	Map<String, Map<String, byte[]>> getDir(String serverName);

    Map<String, byte[]> getDir(String serverName, String dirName);

	boolean testPortIsUsed(String port);

	List<String> getFreePortList(List<String> portList);
	
	/**
	 * @param serverName 服务器的名称
	 * @param dirName 服务器下的jcf-etc目录下的目录名称
	 * @return dirName目录下包含的文件夹或文件，Map的key表示文件夹或文件的名称，value为true表示为文件夹，value为false表示为文件 
	 */
	public Map<String, Boolean> getDirAndFileName(String serverName,String dirName);
	
	public Map<String, String> getNodeControllerConfig();
	
	public void setNodeControllerConfig(Map<String,String> map);
}
