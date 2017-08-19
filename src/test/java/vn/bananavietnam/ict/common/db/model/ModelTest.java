package vn.bananavietnam.ict.common.db.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class ModelTest {

	@Test
	public void testAllGetSetModels() throws ClassNotFoundException, IOException,
								IllegalAccessException, IllegalArgumentException,
								InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String packageName = "vn.bananavietnam.ict.common.db.model";
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
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void testClass(Class<?> classItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if (classItem.getName().endsWith("Criterion")) {
			classItem.getDeclaredConstructor(String.class).newInstance("HERESMYARG");
			classItem.getDeclaredConstructor(String.class, Object.class).newInstance("HERESMYARG", null);
			classItem.getDeclaredConstructor(String.class, Object.class, Object.class).newInstance("HERESMYARG", null, null);
			classItem.getDeclaredConstructor(String.class, Object.class, Object.class, String.class).newInstance("HERESMYARG", null, null, "HERESMYARG");
			classItem.getDeclaredConstructor(String.class, Object.class, String.class).newInstance("HERESMYARG", null, "HERESMYARG");
			classItem.getDeclaredConstructor(String.class, Object.class, String.class).newInstance("HERESMYARG", new ArrayList<String>(), "HERESMYARG");
		}
		
		//coverage for addCriterion null condition
		if (classItem.getName().endsWith("Criteria")) {
			try {
				Object a = classItem.newInstance();
				
				// test for protected void addCriterion(String condition)
				Method method = classItem.getSuperclass().getDeclaredMethod("addCriterion", String.class);
				String []conditions1 ={"test", null};
				for (String condition : conditions1) {
					try {
						method.invoke(a, condition);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// test for protected void isValid()
				method = classItem.getSuperclass().getDeclaredMethod("isValid");
				method.invoke(a);

				// test for protected void addCriterion(String condition, Object value, String property)
				method = classItem.getSuperclass().getDeclaredMethod("addCriterion", String.class, Object.class, String.class);
				Object [] conditions2 ={new Object(), null};
				for (Object condition : conditions2) {
					try {
						method.invoke(a, "test", condition, "test");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// test for protected void addCriterion(String condition, Object value1, Object value2, String property)
				method = classItem.getSuperclass().getDeclaredMethod("addCriterion", String.class, Object.class, Object.class, String.class);
				Object [] conditions3 ={new Object(), null};
				for (Object conditionA : conditions3) {
					for (Object conditionB : conditions3) {
						try {
							method.invoke(a, "test", conditionA, conditionB, "test");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				// test for protected void addCriterionForJDBCDate(String condition, List<Date> values, String property)
				method = classItem.getSuperclass().getDeclaredMethod("addCriterionForJDBCDate", String.class, List.class, String.class);
				ArrayList<List<Date>> conditions4 = new ArrayList<List<Date>>();
				conditions4.add(null);
				conditions4.add(new ArrayList<Date>());
				ArrayList<Date> tmp = new ArrayList<Date>();
				tmp.add(new Date());
				conditions4.add(tmp);
				for (List<Date> condition : conditions4) {
					try {
						method.invoke(a, "test", condition, "test");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// test for protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property)
				method = classItem.getSuperclass().getDeclaredMethod("addCriterionForJDBCDate", String.class, Date.class, Date.class, String.class);
				Date [] conditions5 ={new Date(), null};
				for (Date conditionA : conditions5) {
					for (Date conditionB : conditions5) {
						try {
							method.invoke(a, "test", conditionA, conditionB, "test");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (Method method : classItem.getMethods()) {
			if (method.getName().equals("or")) {
				Class<?>[] params = method.getParameterTypes();				
				if (params.length == 0) {	
					method.invoke(classItem.newInstance(), new Object[] {});
				} else {
					method.invoke(classItem.newInstance(), new Object[] {null});
				}								
			} else if (method.getName().startsWith("get") || method.getName().startsWith("is")
					|| method.getName().equals("clear")) {
				try {
					method.invoke(classItem.newInstance(), new Object[] {});
				} catch (Exception ex) {					
					try {
						method.invoke(classItem.getDeclaredConstructor(String.class).newInstance("HERESMYARG"), new Object[] {});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			} else if (method.getName().equals("createCriteria")) {
				//call twice to cover "if (oredCriteria.size() == 0)"
				Object tmp = classItem.newInstance();
				method.invoke(tmp, new Object[] {});
				method.invoke(tmp, new Object[] {});
			} else if (method.getName().startsWith("set") || method.getName().startsWith("and")) {
				Class<?>[] params = method.getParameterTypes();				
				if (params.length == 0) {					
					try {
						method.invoke(classItem.newInstance());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (params.length == 2) {
					if (params[0].equals(Boolean.class)) {
						try {
							method.invoke(classItem.newInstance(), new Object[]{true, true});
						} catch (Exception ex) {							
							ex.printStackTrace();
						}
					} else if (params[0].equals(Date.class)) {
						try {
							method.invoke(classItem.newInstance(), new Object[]{new Date(), new Date()});	
						} catch (Exception ex) {							
							ex.printStackTrace();
						}				
					} else if (params[0].equals(String.class)) {
						try {
							method.invoke(classItem.newInstance(), new Object[]{"5", "6"});
						} catch (Exception ex) {							
							ex.printStackTrace();
						}
					} else if (params[0].equals(Integer.class)) {
						try {
							method.invoke(classItem.newInstance(), new Object[]{new Integer(0), new Integer(0)});
						} catch (Exception ex) {							
							ex.printStackTrace();
						}					
					} else {
						try {
							method.invoke(classItem.newInstance(), new Object[]{new Double(0), new Double(0)});	
						} catch (Exception ex) {							
							ex.printStackTrace();
						}					
					}
				} else {
					if (params[0].equals(List.class)) {
						try {
							ArrayList<Date> tmp = new ArrayList<Date>();
							tmp.add(new Date());
							method.invoke(classItem.newInstance(), tmp);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else if (params[0].equals(Boolean.class) || params[0].equals(boolean.class)) {
						try {
							method.invoke(classItem.newInstance(), true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else if (params[0].equals(Date.class)) {
						try {
							method.invoke(classItem.newInstance(), new Date());
							Date date1 = null; 
							method.invoke(classItem.newInstance(), date1);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else if (params[0].equals(String.class)) {
						try {
							method.invoke(classItem.newInstance(), "");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else if (params[0].equals(Integer.class)) {
						try {
							method.invoke(classItem.newInstance(), new Integer(0));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						try {
							method.invoke(classItem.newInstance(), new Double(0));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
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
			/*if (!file.getName().contains("Example") && !file.getName().contains("Key")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}*/
			//if (file.getName().contains("Example")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			//}
		}
		return classes;
	}
}
