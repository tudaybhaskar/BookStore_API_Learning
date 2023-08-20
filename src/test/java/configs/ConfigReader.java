package configs;

import java.util.Properties;
import java.io.FileInputStream;

public class ConfigReader {
	
	private Properties properties;
	private static ConfigReader configReader;
	String properties_filePath = "./configs/config.properties" ;
	
	private ConfigReader() {
		
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(properties_filePath ) ;
			properties = new Properties();
			try {
				properties.load(fis);
				fis.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Config.properties file is not found at" + properties_filePath ) ;
		}
	}
	
	public static ConfigReader getInstance() {
		if( configReader == null ) {
			configReader = new ConfigReader() ;
		}
		return configReader ;
	}
	
	public String getBaseUrl() {
		String baseUrl = properties.getProperty( "base_Url" ) ;
		if(baseUrl != null) {
			return baseUrl;
		}else {
			throw new RuntimeException("base_Url is not specified in the configs/config.properties file") ;
		}
	}
	
	public String getUserId() {
		String user_Id = properties.getProperty( "user_Id" ) ;
		if(user_Id != null) {
			return user_Id;
		}else {
			throw new RuntimeException("user_Id is not specified in the configs/config.properties file") ;
		}
	}
}
