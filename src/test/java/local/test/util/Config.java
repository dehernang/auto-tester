package local.test.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author hernan
 * @version 1.0
 * 
 */
public class Config {

	private Properties prop;
	private InputStream input = null;
	private Map<String, String> properties;
	private String basePath;

	/*
	 * @file e.g. config.properties
	 */
	public Config(String filename) {
		prop = new Properties();
		properties = new HashMap<String, String>();
		try {
			Path path = Paths.get(Config.class.getResource(".").toURI());
			// for move 5 dirs down, better than base.dir for now
			basePath = path.getParent().getParent().getParent().getParent()
					.getParent().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init(filename);
	}

	public void init(String file) {
		try {
			input = new FileInputStream(basePath + "/configs/" + file);
			prop.load(input);
			Set<Object> keys = prop.keySet();
			for (Object key : keys) {
				properties
						.put(key.toString(), prop.getProperty(key.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param key
	 * @return String A property by key
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * 
	 * @return Map All properties
	 */
	public Map<String, String> getProperties() {
		return this.properties;
	}

	/**
	 * 
	 * @return project base path
	 */
	public String getBasePath() {
		return this.basePath;
	}

}
