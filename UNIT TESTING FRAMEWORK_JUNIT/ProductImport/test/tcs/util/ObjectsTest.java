package test.tcs.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.InjectMocks;

import tcs.util.Objects;

public class ObjectsTest {

	@InjectMocks
	private final Objects testInstance = new Objects();

	@Test
	public void isNullObjectWithStringValue() {
		boolean result = testInstance.isNull(new String("Test"));
		assertEquals(false, result);
	}

	@Test
	public void isNullForNullObject() {
		boolean result = testInstance.isNull(null);
		assertEquals(true, result);
	}

	@Test
	public void isNotNullWithStringValue() {
		boolean result = testInstance.isNotNull(new String("Test"));
		assertEquals(true, result);
	}

	@Test
	public void isNotNullObjectWithNullObject() {
		boolean result = testInstance.isNotNull(null);
		assertEquals(false, result);
	}

	@Test
	public void isEmptyString() {
		boolean result = testInstance.isEmpty(new String());
		assertEquals(true, result);
	}

	@Test
	public void isEmptyStringWithStringValue() {
		boolean result = testInstance.isEmpty("Test");
		assertEquals(false, result);
	}

	@Test
	public void isEmptyStringWithNullValue() {
		boolean result = testInstance.isEmpty(null);
		assertEquals(true, result);
	}

	@Test
	public void isEmptyStringWithArrayValue() {
		boolean result = testInstance.isEmpty(new int[0]);
		assertEquals(true, result);
	}

	@Test
	public void isEmptyStringWithNonEmptyArrayValue() {
		int[] test = new int[] { 1, 2, 3 };
		boolean result = testInstance.isEmpty(test);
		assertEquals(false, result);
	}

	@Test
	public void isEmptyStringWithList() {
		boolean result = testInstance.isEmpty(new ArrayList());
		assertEquals(true, result);
	}

	@Test
	public void isEmptyStringWithMap() {
		boolean result = testInstance.isEmpty(new HashMap());
		assertEquals(true, result);
	}

	@Test
	public void isEmptyStringWithCollection() {
		boolean result = testInstance.isEmpty(new Object());
		assertEquals(false, result);
	}

	@Test
	public void isNotEmptyStringWithStringValu() {
		boolean result = testInstance.isNotEmpty("Test");
		assertEquals(true, result);
	}

	@Test
	public void isNotEmptyStringWithEmptyValue() {
		boolean result = testInstance.isNotEmpty(new String());
		assertEquals(false, result);
	}

}
