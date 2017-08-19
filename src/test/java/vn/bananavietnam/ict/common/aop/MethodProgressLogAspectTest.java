package vn.bananavietnam.ict.common.aop;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.util.Util;

/**
 * The class <code>MethodProgressLogAspectTest</code> contains tests for the class <code>{@link MethodProgressLogAspect}</code>.
 *
 * @generatedBy CodePro at 17/05/04 15:46
 * @author pc30
 * @version $Revision: 1.0 $
 */
public class MethodProgressLogAspectTest {
	
	@InjectMocks
	private MethodProgressLogAspect aspect;
	
	@Mock
	Util util;	
	
	public void createUserLogin() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0009");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
	}
	
	/**
	 * Run the MethodProgressLogAspect() constructor test.
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testMethodProgressLogAspect_1()
		throws Exception {
		MethodProgressLogAspect result = new MethodProgressLogAspect();
		assertNotNull(result);
		// add additional test code here
	}
		
	/**
	 * Run the void before(JoinPoint) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:13
	 */
	@Test
	public void testBefore() throws Exception {
		MockitoAnnotations.initMocks(this);
		createUserLogin();
		JoinPoint jp = Mockito.mock(JoinPoint.class);
		// add mock object expectations here
		
		Object target = Mockito.mock(Object.class); 
		when(jp.getTarget()).thenReturn(target);
		
		Signature sig = Mockito.mock(Signature.class); 
		when(jp.getSignature()).thenReturn(sig);
		
		String[] args = {"test"};
		when(jp.getArgs()).thenReturn(args);
		
		aspect.before(jp);

		Mockito.verify(jp).getArgs();
	}
	
	
	/**
	 * Run the void before(JoinPoint) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:13
	 */
	@Test
	public void testAfter() throws Exception {
		MockitoAnnotations.initMocks(this);
		createUserLogin();
		JoinPoint jp = Mockito.mock(JoinPoint.class);
		// add mock object expectations here
		
		Object target = Mockito.mock(Object.class); 
		when(jp.getTarget()).thenReturn(target);
		
		Signature sig = Mockito.mock(Signature.class); 
		when(jp.getSignature()).thenReturn(sig);
		
		String[] args = {"test"};
		when(jp.getArgs()).thenReturn(args);
		
		aspect.after(jp);

		Mockito.verify(jp).getArgs();
	}
}