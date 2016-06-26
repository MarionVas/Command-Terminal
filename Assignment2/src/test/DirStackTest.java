package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import a2.DirStack;

public class DirStackTest 
{
  private DirStack dStack;
  private Stack<String> testStack;

  @Before
  public void setUp() throws Exception 
  {
    dStack = new DirStack();
    testStack = new Stack<String>();
  }

  @Test
  public void testPushD() 
  {
    dStack.pushD("taco");
    assertEquals(1, dStack.getStack().size());
  }
  
  public void testPushD1() 
  {
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    assertEquals(3, dStack.getStack().size());
  }
  
  public void testPushD2() 
  {
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    testStack.push("taco");
    testStack.push("taco1");
    testStack.push("taco2");
    assertEquals(testStack, dStack.getStack());
  }
  
  @Test
  public void testPopD() 
  {
    dStack.pushD("taco");
    dStack.popD();
    assertEquals(0, dStack.getStack().size());
  }
  
  @Test
  public void testPopD1() 
  {
    dStack.pushD("taco");
    String popped = dStack.popD();
    assertEquals("taco", popped);
  }
  
  public void testPopD2() 
  {
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    dStack.popD();
    testStack.push("taco");
    testStack.push("taco1");
    testStack.push("taco2");
    testStack.pop();
    assertEquals(testStack, dStack.getStack());
  }

}
