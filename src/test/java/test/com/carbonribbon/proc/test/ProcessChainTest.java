package test.com.carbonribbon.proc.test;

import com.carbonribbon.proc.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.com.carbonribbon.proc.test.impl.StringIndentProcessLink;
import test.com.carbonribbon.proc.test.impl.StringPadProcessLink;

/**
 * Unit tests for {@link ProcessChain}
 */
public class ProcessChainTest {

    private static final String TEST_STRING = "TEST";
    private static final String INDENT_EXPECTED = " TEST";
    private static final String INDENT_EXPECTED2 = "  TEST";
    private static final String INDENT_PAD_EXPECTED = "+ TEST";
    //    private static final String OTHER_EXPECTED = "+TEST";
    private static final String PAD_STRING = "+";

    private ProcessLinkFactory extendedDefaultLinkFactory;

    @Before
    public void setup() {
        extendedDefaultLinkFactory = new DefaultProcessLinkFactory() {
            @Override
            public <T> ProcessLink<T> newProcessLink(Class<? extends ProcessLink<T>> clazz) {
                if (StringPadProcessLink.class.equals(clazz)) {
                    return (ProcessLink<T>) new StringPadProcessLink("+");
                }
                return super.newProcessLink(clazz);
            }
        };
    }

    @Test
    public void testChainWithZeroLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        Assert.assertEquals(chain.applyChain(TEST_STRING), TEST_STRING);
    }

    @Test
    public void testChainWithOneLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        chain.addLink(StringIndentProcessLink.class);
        Assert.assertEquals(chain.applyChain(TEST_STRING), INDENT_EXPECTED);
    }

    @Test
    public void testChainWithTwoLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        chain.addLink(StringIndentProcessLink.class);
        chain.addLink(StringIndentProcessLink.class);
        Assert.assertEquals(chain.applyChain(TEST_STRING), INDENT_EXPECTED2);
    }

    @Test
    public void testChainWithTwoDifferentLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        chain.addLink(StringIndentProcessLink.class);
        chain.addLink(new StringPadProcessLink(PAD_STRING));
        Assert.assertEquals(chain.applyChain(TEST_STRING), INDENT_PAD_EXPECTED);
    }

    @Test
    public void testChainWithTwoDifferentLinksCustomFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>(extendedDefaultLinkFactory);
        chain.addLink(StringIndentProcessLink.class);
        chain.addLink(StringPadProcessLink.class);
        Assert.assertEquals(chain.applyChain(TEST_STRING), INDENT_PAD_EXPECTED);
    }

    @Test
    public void testChainWithOneConditionalNegativeLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        chain.addLink(StringIndentProcessLink.class, s -> !TEST_STRING.equals(s));
        Assert.assertEquals(chain.applyChain(TEST_STRING), TEST_STRING);
    }

    @Test
    public void testChainWithOneConditionalPositiveLinksDefaultFactory() {
        ProcessChain<String> chain = new LinkedProcessChain<>();
        chain.addLink(StringIndentProcessLink.class, TEST_STRING::equals);
        Assert.assertEquals(chain.applyChain(TEST_STRING), INDENT_EXPECTED);
    }

}
