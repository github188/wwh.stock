package net.ryian.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.ryian.commons.BeanUtils;

import org.junit.BeforeClass;
import org.junit.Test;

public class BeanUtilsTest{

	@BeforeClass
	public static void beforeClass() {
	}
	
	public class Person {
		public Person() {
			
		}
		private String username;
		private int age;
		private boolean valid;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public boolean isValid() {
			return valid;
		}
		public void setValid(boolean valid) {
			this.valid = valid;
		}
	}
	
	@Test
	public void forceGetProperty() {
		Person p = new Person();
		p.setAge(10);
		p.setUsername("test");
		try {
			assertEquals(BeanUtils.forceGetProperty(p, "age"), 10);
			assertEquals(BeanUtils.forceGetProperty(p, "username"), "test");
		} catch (Exception e) {
			assertNull(e);
		}
	}
	
	@Test
	public void forceSetProperty() {
		Person p = new Person();
		try {
			BeanUtils.forceSetProperty(p, "username", "test");
			BeanUtils.forceSetProperty(p, "age", 10);
		} catch (NoSuchFieldException e) {
			assertNull(e);
		}
		assertEquals(p.getUsername(), "test");
		assertEquals(p.getAge(), 10);
	}
	
	
	@Test
	public void getSetterName() {
		assertEquals("setUsername",BeanUtils.getSetterName("username"));
		assertEquals("setValid",BeanUtils.getSetterName("valid"));
	}
	
	@Test
	public void getGetterName() {
		try {
			assertEquals("getUsername", BeanUtils.getGetterName(Person.class, "username"));
			assertEquals("isValid", BeanUtils.getGetterName(Person.class, "valid"));
		} catch (NoSuchFieldException e) {
			assertNull(e);
		}
		
	}
	
	@Test
	public void separateClassName() {
		String className = "UserInfo";
		String separator = "-";
		boolean lowerCase = true;
		assertEquals("user-info",BeanUtils.separateClassName(className, separator, lowerCase));
	}
	
}
