package test.com.carbonribbon.proc.test;

import com.carbonribbon.proc.ProcessLink;
import org.junit.Assert;
import org.junit.Test;
import test.com.carbonribbon.proc.test.impl.StringIndentProcessLink;
import test.com.carbonribbon.proc.test.impl.StringPadProcessLink;

/**
 * Unit tests for {@link ProcessLink}
 */
public class ProcessLinkTest {

    private static final String TEST_STRING = "TEST";
    private static final String INDENT_EXPECTED = " TEST";
    private static final String INDENT_EXPECTED2 = "  TEST";
    private static final String PAD_EXPECTED = "+TEST";
    private static final String INDENT_PAD_EXPECTED = "+ TEST";
    private static final String PAD_STRING = "+";

    @Test
    public void testProcessLinkApply() {
        ProcessLink<String> stringIndentProcessLink = new StringIndentProcessLink();
        Assert.assertEquals(stringIndentProcessLink.apply(TEST_STRING), INDENT_EXPECTED);
    }

    @Test
    public void testRepeatedProcessLinkApply() {
        ProcessLink<String> stringIndentProcessLink = new StringIndentProcessLink();
        String ss = stringIndentProcessLink.apply(TEST_STRING);
        Assert.assertEquals(stringIndentProcessLink.apply(ss), INDENT_EXPECTED2);
    }

    @Test
    public void testDifferentProcessLinkApply() {
        ProcessLink<String> stringIndentProcessLink = new StringIndentProcessLink();
        ProcessLink<String> stringPadProcessLink = new StringPadProcessLink(PAD_STRING);
        String indentedString = stringIndentProcessLink.apply(TEST_STRING);
        Assert.assertEquals(indentedString, INDENT_EXPECTED);
        Assert.assertEquals(stringPadProcessLink.apply(TEST_STRING), PAD_EXPECTED);
        Assert.assertEquals(stringPadProcessLink.apply(indentedString), INDENT_PAD_EXPECTED);
    }
}
