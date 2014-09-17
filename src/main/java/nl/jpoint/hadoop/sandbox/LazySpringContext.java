package nl.jpoint.hadoop.sandbox;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LazySpringContext {

	private static Map<String, ClassPathXmlApplicationContext> contextMap = new HashMap<>();

	private static void initContext(String propertiesString) {
		ClassPathXmlApplicationContext springContext =
				new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"}, false);

		Properties properties = new Properties();
		try {
			properties.load(new StringReader(propertiesString));
		} catch (IOException e) {
			e.printStackTrace();
		}

		PropertySource<?> propertySource = new PropertiesPropertySource("configProperties", properties);
		springContext.getEnvironment().getPropertySources().addFirst(propertySource);

		springContext.refresh();

		contextMap.put(propertiesString, springContext);

		System.out.println("Spring context initialized");
	}

	public static synchronized void autowireBean(Configuration conf, Object bean) {
		String propertiesString = conf.get("nl.jpoint.properties");

		if (!contextMap.containsKey(propertiesString)) {
			initContext(propertiesString);
		}

		contextMap.get(propertiesString).getAutowireCapableBeanFactory().autowireBean(bean);
	}

}
