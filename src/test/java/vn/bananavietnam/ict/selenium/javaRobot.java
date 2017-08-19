package vn.bananavietnam.ict.selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.gargoylesoftware.htmlunit.javascript.host.event.InputEvent;

public class javaRobot {

	  Robot robot = new Robot();

	  
	  public javaRobot() throws AWTException
	  {
	    robot.setAutoDelay(1000);
	    robot.setAutoWaitForIdle(false);
	    
	    robot.delay(1000);
	    robot.mouseMove(0, 16);
	    type("abcxyz");
	    type(KeyEvent.VK_ENTER);
	    robot.delay(1000);
	  }
	  
	  
	  private void leftClick()
	  {
	    robot.mousePress(InputEvent.ALT_MASK);
	    robot.delay(200);
	    robot.mouseRelease(InputEvent.ALT_MASK);
	    robot.delay(200);
	  }
	  
	  private void type(int i)
	  {
	    robot.delay(40);
	    robot.keyPress(i);
	    robot.keyRelease(i);
	  }
	  private void doType(int[] keyCodes, int offset, int length) {
	      if (length == 0) {
	          return;
	      }

	      robot.keyPress(keyCodes[offset]);
	      doType(keyCodes, offset + 1, length - 1);
	      robot.keyRelease(keyCodes[offset]);
	  }

	  private void doType(int... keyCodes) {
	      doType(keyCodes, 0, keyCodes.length);
	  }
	  private void type(String s)
	  {
	    byte[] bytes = s.getBytes();
	    for (byte b : bytes)
	    {
	      int code = b;
	      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
	      if (code > 1 && code < 123) code = code - 32;
	      robot.delay(40);
	      robot.keyPress(code);
	    }
	  }
}
