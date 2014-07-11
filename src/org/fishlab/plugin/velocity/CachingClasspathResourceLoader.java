package org.fishlab.plugin.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public class CachingClasspathResourceLoader extends ResourceLoader {
	private Map<String, URL> urlMap = new HashMap<String, URL>();

	public void init(ExtendedProperties configuration) {
	}

	public synchronized InputStream getResourceStream(String resourceName)
			throws ResourceNotFoundException {
		try {
			URL url = getURL(resourceName);

			if (url == null) {
				throw new ResourceNotFoundException("Can not find resource: "
						+ resourceName);
			}
			return url.openStream();
		} catch (IOException e) {
			throw new ResourceNotFoundException("Can not find resource: "
					+ resourceName + " - Reason: " + e.getMessage());
		}
	}

	public long getLastModified(Resource res) {
		try {
			URL url = getURL(res.getName());
			long lm = url.openConnection().getLastModified();
			return lm;
		} catch (Exception e) {
			rsvc.error(e);
			return 0;
		}
	}

	public boolean isSourceModified(Resource res) {
		long lastModified = getLastModified(res);
		return (lastModified != res.getLastModified());
	}

	private URL getURL(String rn) {
		if (urlMap.containsKey(rn)) {
			return (URL) urlMap.get(rn);
		}

		ClassLoader cl = this.getClass().getClassLoader();
		URL url = cl.getResource(rn);

		if (url != null) {
			urlMap.put(rn, url);
		}

		return url;
	}
}