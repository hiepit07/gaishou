package vn.bananavietnam.ict.banana.component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class ComponentTest {

	@Test
	public void testAllGetSetComponents() throws ClassNotFoundException, IOException,
								IllegalAccessException, IllegalArgumentException,
								InvocationTargetException, InstantiationException {
		String packageName = "vn.bananavietnam.ict.banana.component";
		Class<?>[] classesArray = getClasses(packageName);
		for (Class<?> classItem : classesArray) {
			testClass(classItem);
		}
	}

	/**
	 * Test all getter/setter methods of classes in array
	 * 
	 * @param classItem : one class in classes array
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private void testClass(Class<?> classItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		for (Method method : classItem.getMethods()) {
			if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
				method.invoke(classItem.newInstance(), new Object[] {});
			} else if (method.getName().startsWith("set")) {
				Class<?>[] params = method.getParameterTypes();
				if (params[0].equals(boolean.class)) {
					method.invoke(classItem.newInstance(), true);
				} else if (params[0].equals(Boolean.class)) {
					method.invoke(classItem.newInstance(), true);
				} else if (params[0].equals(java.util.Date.class)) {
					method.invoke(classItem.newInstance(), new java.util.Date());
				} else if (params[0].equals(Date.class)) {
					method.invoke(classItem.newInstance(), new Date(Calendar.getInstance().getTimeInMillis()));
				} else if (params[0].equals(String.class)) {
					method.invoke(classItem.newInstance(), "");
				} else if (params[0].equals(int.class)) {
					method.invoke(classItem.newInstance(), 0);
				} else if (params[0].equals(Integer.class)) {
					method.invoke(classItem.newInstance(), new Integer(0));
				} else if (params[0].equals(Double.class)) {
					method.invoke(classItem.newInstance(), new Double(0));
				} else {
					// if (params[0].equals(ArrayList.class))
					method.invoke(classItem.newInstance(), new ArrayList<Object>());
				}
			}
		}
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName : The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile().replaceAll("test-classes", "classes").replaceAll("%20", " ")));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and sub
	 * dirs.
	 *
	 * @param directory : The base directory
	 * @param packageName : The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		File[] files = directory.listFiles();
		for (File file : files) {
			classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
		}
		return classes;
	}
}
