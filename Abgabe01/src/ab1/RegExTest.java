package ab1;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import ab1.impl.Eberl.RegExImpl;

/**
 * JUnit-Tests
 * 
 * @author Raphael Wigoutschnigg
 *
 */
public class RegExTest {
	private final RegEx sol = new RegExImpl();

	@Test
	public void testRegexGeradeLaenge() {
		String regEx = sol.getRegexGeradeLaenge();

		Assert.assertEquals(true, Pattern.matches(regEx, "aaaaab"));
		Assert.assertEquals(true, Pattern.matches(regEx, ""));
		Assert.assertEquals(true, Pattern.matches(regEx, "ab"));
		Assert.assertEquals(true, Pattern.matches(regEx, "zzzz"));
		Assert.assertEquals(true, Pattern.matches(regEx, "ab ab ab ab ab"));
		Assert.assertEquals(true, Pattern.matches(regEx, "ab.ab.ab.ab.ab"));

		Assert.assertEquals(false, Pattern.matches(regEx, "a"));
		Assert.assertEquals(false, Pattern.matches(regEx, "."));
		Assert.assertEquals(false, Pattern.matches(regEx, ".a."));
		Assert.assertEquals(false, Pattern.matches(regEx, "hallo"));
	}

	@Test
	public void testRegexGanzeZahlen() {
		String regEx = sol.getRegexGanzeZahlen();

		Assert.assertEquals(true, Pattern.matches(regEx, "1"));
		Assert.assertEquals(true, Pattern.matches(regEx, "10"));
		Assert.assertEquals(true, Pattern.matches(regEx, "+17"));
		Assert.assertEquals(true, Pattern.matches(regEx, "-20"));
		Assert.assertEquals(true, Pattern.matches(regEx, "+100"));
		Assert.assertEquals(true, Pattern.matches(regEx, "0"));

		Assert.assertEquals(false, Pattern.matches(regEx, "-0"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+0"));
		Assert.assertEquals(false, Pattern.matches(regEx, "01"));
		Assert.assertEquals(false, Pattern.matches(regEx, "000"));
		Assert.assertEquals(false, Pattern.matches(regEx, "a19"));
		Assert.assertEquals(false, Pattern.matches(regEx, "0xAB"));
		Assert.assertEquals(false, Pattern.matches(regEx, "0.78f"));
	}

	@Test
	public void testRegexTelnummer() {
		String regEx = sol.getRegexTelnummer();

		Assert.assertEquals(true, Pattern.matches(regEx, "+436801234567"));
		Assert.assertEquals(true, Pattern.matches(regEx, "+1 123 1234567"));
		Assert.assertEquals(true, Pattern.matches(regEx, "+43 680 1234567"));
		Assert.assertEquals(true, Pattern.matches(regEx, "0043 680 1234567"));
		Assert.assertEquals(true, Pattern.matches(regEx, "00436801234567"));
		Assert.assertEquals(true, Pattern.matches(regEx, "+11231234567"));

		Assert.assertEquals(false, Pattern.matches(regEx, "+43 680 12 34 567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+ 43 680 12 34 567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+ 4 3 6 8 0 1 2 3 4 5 6 7"));
		Assert.assertEquals(false, Pattern.matches(regEx, "0043 680 12 34 567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "00 43 680 12 34 567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "0 0 4 3 6 8 0 1 2 3 4 5 6 7"));
		Assert.assertEquals(false, Pattern.matches(regEx, "06801234567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+43.680.1234567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+43 680a1234567"));
		Assert.assertEquals(false, Pattern.matches(regEx, "+43-680-1234567"));
	}

	@Test
	public void testRegexDatum() {
		String regEx = sol.getRegexDatum();

		Assert.assertEquals(true, Pattern.matches(regEx, "01.01.2012"));
		Assert.assertEquals(true, Pattern.matches(regEx, "1.1.2012"));
		Assert.assertEquals(true, Pattern.matches(regEx, "11.1.2012"));
		Assert.assertEquals(true, Pattern.matches(regEx, "1.11.2012"));

		Assert.assertEquals(false, Pattern.matches(regEx, "1. Jänner 2012"));
		Assert.assertEquals(false, Pattern.matches(regEx, "1.1.16"));
		Assert.assertEquals(false, Pattern.matches(regEx, "01.01.16"));
	}

	@Test
	public void testRegexDomainName() {
		String regEx = sol.getRegexDomainName();

		Assert.assertEquals(true, Pattern.matches(regEx, "www.aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "campus.aau.at"));

		Assert.assertEquals(false, Pattern.matches(regEx, "w-w-w.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www.aau.ataeh"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www.auto"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www"));
		Assert.assertEquals(false, Pattern.matches(regEx, "at"));
		Assert.assertEquals(false, Pattern.matches(regEx, ".www.aau.at"));
	}

	@Test
	public void testRegexEmail() {
		String regEx = sol.getRegexEmail();

		Assert.assertEquals(true, Pattern.matches(regEx, "jemand@aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "jemand.anderes@aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "jemand-anderes@aau.at"));

		Assert.assertEquals(false, Pattern.matches(regEx, "aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "@aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "jemand@at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "9jemand@aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, ".jemand@aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "-jemand@aau.at"));

	}

	@Test
	public void testRegexURL() {
		String regEx = sol.getRegexURL();

		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "ftp://aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "https://campus.aau.at"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80/"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80/login"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80/login.intern"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80/login-intern"));
		Assert.assertEquals(true, Pattern.matches(regEx, "http://www.aau.at:80/sub/sub.sub/sub-sub-sub/"));

		Assert.assertEquals(false, Pattern.matches(regEx, "ssh://www.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "://aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "http:/campus.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "http//campus.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "//campus.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "w-w-w.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www.aau.ataeh"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www.auto"));
		Assert.assertEquals(false, Pattern.matches(regEx, "www"));
		Assert.assertEquals(false, Pattern.matches(regEx, "at"));
		Assert.assertEquals(false, Pattern.matches(regEx, ".www.aau.at"));
		Assert.assertEquals(false, Pattern.matches(regEx, "http://www.aau.at:-80"));
		Assert.assertEquals(false, Pattern.matches(regEx, "http://www.aau.at:80login"));
		Assert.assertEquals(false, Pattern.matches(regEx, "http://www.aau.at:80a/login.intern"));
	}

	@Test
	public void testRegexVereinfachen1() {
		String regEx = sol.getRegexVereinfachen1();

		Assert.assertEquals(true, regEx.length() <= 20);

		Assert.assertEquals(true, Pattern.matches(regEx, "aaaaaaaaaa"));
		Assert.assertEquals(true, Pattern.matches(regEx, "bbbbbbbbbb"));
		Assert.assertEquals(true, Pattern.matches(regEx, "bbaabbaabb"));
		Assert.assertEquals(true, Pattern.matches(regEx, "ababababab"));
		Assert.assertEquals(true, Pattern.matches(regEx, "cccccccccc"));
		Assert.assertEquals(true, Pattern.matches(regEx, "abcdabcdab"));

		Assert.assertEquals(false, Pattern.matches(regEx, "abcd"));
		Assert.assertEquals(false, Pattern.matches(regEx, "abcdabcdabcd"));
		Assert.assertEquals(false, Pattern.matches(regEx, "eeeeeeeeee"));

	}

	@Test
	public void testRegexVereinfachen2() {
		String regEx = sol.getRegexVereinfachen2();

		Assert.assertEquals(true, regEx.length() <= 10);

		Assert.assertEquals(true, Pattern.matches(regEx, "abab"));
		Assert.assertEquals(true, Pattern.matches(regEx, "aa"));
		Assert.assertEquals(true, Pattern.matches(regEx, "aaa"));
		Assert.assertEquals(true, Pattern.matches(regEx, "aaaa"));
		Assert.assertEquals(true, Pattern.matches(regEx, "bbbb"));
		Assert.assertEquals(true, Pattern.matches(regEx, "baaa"));

		Assert.assertEquals(false, Pattern.matches(regEx, "a"));
		Assert.assertEquals(false, Pattern.matches(regEx, "aaaaa"));
		Assert.assertEquals(false, Pattern.matches(regEx, "dd"));
	}
}
