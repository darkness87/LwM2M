package AMI;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.09.07
 */
public class JniConfig{

	private Properties properties = null;

	public JniConfig() {

		properties = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream("src/main/resources/JniConfig.properties");
			properties.load(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			File profile = new File("src/main/resources/JniConfig.properties");
			FileOutputStream upfile = null;
			try {
				upfile = new FileOutputStream(profile);
				
				String userDir = System.getProperty("user.dir");
				
				properties.setProperty("jni.path.windows", userDir+"/src/main/resources/");
				properties.setProperty("jni.path.linux", userDir+"/src/main/resources/");
				
				try {
					properties.store(upfile,null);
					upfile.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("JniConfig.properties / File Created");
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getJniPath() {
		JniConfig config = new JniConfig();
		String osName = System.getProperty("os.name");
		String val = "";
		
		if(osName.matches(".*Windows.*")) {
			val = config.getProperties().getProperty("jni.path.windows");
		}else {
			val = config.getProperties().getProperty("jni.path.linux");
		}
		
		String userDir = System.getProperty("user.dir");
		
		if(val==null||val==""||val.equals(null)||val.equals("")) {
			
			if(osName.matches(".*Windows.*")) {
				val=userDir+"/src/main/resources/";
				propertiesAdd("jni.path.windows", val);
			}else {
				val=userDir+"/src/main/resources/";
				propertiesAdd("jni.path.linux", val);
			}
		}
		return val;
	}
	
	public void propertiesAdd(String pro, String val) {
		
		File profile = new File("src/main/resources/JniConfig.properties");
		FileOutputStream upfile = null;
		try {
			upfile = new FileOutputStream(profile);
			properties.setProperty(pro, val);
			try {
				properties.store(upfile,null);
				upfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("JniConfig.properties / "+pro+" / "+val+" / create");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
