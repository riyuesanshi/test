package com.sml.test1;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

import com.sun.jndi.url.rmi.rmiURLContextFactory;

/**
 * 自定义rmi连接工厂
 * 
 * @author zhanghua
 * 
 */
public class SunRmiURLContextFactory extends rmiURLContextFactory implements
		javax.naming.spi.InitialContextFactory {

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {
		return (Context) this.getObjectInstance(null, null, null, environment);
	}

}
