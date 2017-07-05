package ab6.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import ab6.SearchTools;
import ab6.impl.Eberl.SearchToolsImpl;

public class TestSuche {

	SearchTools tools = new SearchToolsImpl();

	
	@Test
	public void test_Suche1() {
		assertEquals(Arrays.asList(), tools.findPattern("ab", "abc"));
	}
	
	@Test
	public void test_Suche2() {
		assertEquals(Arrays.asList(4), tools.findPattern("abababc", "abc"));
	}
	
	@Test
	public void test_Suche3() {
		assertEquals(Arrays.asList(0,1,2,3), tools.findPattern("aaaaa", "aa"));
	}
	
	@Test
	public void test_Suche4() {
		assertEquals(Arrays.asList(0), tools.findPattern("abc", "abc"));
	}

	
	@Test
	public void test_Check1() {
		assertEquals(true, tools.checkPattern("abc", ".*"));
		assertEquals(true, tools.checkPattern("abc", "a.*"));
	}

	@Test
	public void test_Check2() {
		assertEquals(true, tools.checkPattern("abc", ".bc"));
		assertEquals(true, tools.checkPattern("abc", "a.c"));
		assertEquals(true, tools.checkPattern("abc", "ab."));
		assertEquals(true, tools.checkPattern("abc", "..."));
	}
	
	@Test
	public void test_Check3() {
		assertEquals(true, tools.checkPattern("abcaaaaabc", "a.ca*.c"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", ".b.a*.c"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", "a.ca.aa.b."));
	}
	
	@Test
	public void test_Check4() {
		assertEquals(true, tools.checkPattern("abcaaaaabc", ".*aaa.*"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", ".*.*"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", ".*.*abc"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", "abc.*.*abc"));
		assertEquals(true, tools.checkPattern("abcaaaaabc", "abc.*"));
	}
	
	@Test
	public void test_Check5() {
		assertEquals(false, tools.checkPattern("abcaaaaabc", "a.c*a.c"));
		assertEquals(false, tools.checkPattern("abcaaaaabc", "abcaaaaabca"));
		assertEquals(false, tools.checkPattern("abcaaaaabc", "aa.*"));
		assertEquals(false, tools.checkPattern("abcaaaaabc", ".*bb"));
	}
}
