package com.sml.test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class PropertiesTest {
	public static void main(String[] args) {
		File config = new File("./test.properties");
		String a = (String) getPro(config).get("sml");

		System.out.println(a);

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {

				File isNew = new File("./test.properties");
				long lastModifyTime = isNew.lastModified();
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastModifyTime > 3000) {
					System.out.println(getPro(isNew).getProperty("sml"));
				}

			}
		};
		
		Timer timer = new Timer();
		timer.schedule(timerTask, 10000, 5000);
	}

	private static Properties getPro(File config) {

		Properties props = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(config);
			props.load(is);

		} catch (Exception e) {

		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception e) {
				}
		}

		return props;

	}

}
