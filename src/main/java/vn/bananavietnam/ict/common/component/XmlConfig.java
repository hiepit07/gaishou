package vn.bananavietnam.ict.common.component;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains data from config.xml
 * 
 * @author PhongBui
 *
 */
public class XmlConfig {
	private long lastModified;
	public Map<String, Object> mode = new HashMap<String, Object>();
	
    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String[] getScreenList(String name) {
        Object object = mode.get(name);
        if (object != null) {
            return object.toString().split(",");
        } else {
            return new String[0];
        }
    }
}