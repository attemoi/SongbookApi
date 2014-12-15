package fi.attemoisio.songbookapi.resource;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.attemoisio.songbookapi.util.StringUtils;

public class StringUtilsTest {

	@Test
	public void testToSlug() {
		assertEquals("urho-matti-vol-2", StringUtils.toSlug("Urho Matti vol 2"));
		assertEquals("aa-asdf", StringUtils.toSlug("AA''' asdf"));
		assertEquals("foobar", StringUtils.toSlug("fóòbâr"));
		assertEquals("lallall-pyeasd---asdfasdf", StringUtils.toSlug("Lallall, Py'easd - asdfasdf"));
	}

}
