package com.bigdata;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.MethodSorters;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.bigdata.PathErrorException;
import com.bigdata.Tree;
import com.bigdata.MyCallback;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TreeTest{

  static private Tree tree = new Tree();
	
  public static void main(String[] args) throws Exception{

		Result result = JUnitCore.runClasses(TreeTest.class);
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		if (result.wasSuccessful()) {
			System.out.println("All tests finished successfully...");
		}
	}

  @Test
  public void testCreateAndGet() throws PathErrorException{
    tree.watch("/", new MyCallback());
    tree.create("/A", "A"); // 根的孩子
    tree.create("/B", "B"); // A的兄弟
    tree.create("/A/C", "C"); // A的孩子
    tree.create("/A/C/D", "D"); // C的孩子
    tree.create("/A/E", "E");  // C的兄弟
    tree.create("/A/E/G", "G");  // E的孩子
    assertEquals("G", tree.getValue("/A/E/G"));
    assertEquals("B", tree.getValue("/B"));
    tree.setValue("/A/E/G", "new G!");
    assertEquals("new G!", tree.getValue("/A/E/G"));
  }

  @Test(expected = PathErrorException.class)
  public void testPathErrorException1() throws PathErrorException {
      Tree tree = new Tree();
      tree.create("/", "/");
      fail("创建【/】未抛出异常！"); 
  }

  @Test(expected = PathErrorException.class)
  public void testPathErrorException2() throws PathErrorException {
      Tree tree = new Tree();
      tree.create("/BB/FF", "FF");
      fail("创建【/B/F】未抛出异常！"); 
  }
}
