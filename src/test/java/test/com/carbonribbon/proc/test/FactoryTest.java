package test.com.carbonribbon.proc.test;

import com.carbonribbon.proc.DefaultProcessLinkFactory;
import com.carbonribbon.proc.ProcessLink;
import com.carbonribbon.proc.ProcessLinkFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.com.carbonribbon.proc.test.impl.StringIndentProcessLink;
import test.com.carbonribbon.proc.test.impl.StringPadProcessLink;

/**
 * Unit tests for {@link ProcessLinkFactory}
 */
public class FactoryTest {

    private static final String TEST_STRING = "TEST";
    private static final String DEFAULT_EXPECTED = " TEST";
    private static final String OTHER_EXPECTED = "+TEST";

    private ProcessLinkFactory extendedDefaultLinkFactory;

    @Before
    public void setup() {
        extendedDefaultLinkFactory = new DefaultProcessLinkFactory() {
            @Override
            public ProcessLink newProcessLink(Class<? extends ProcessLink> clazz) {
                if (StringPadProcessLink.class.equals(clazz)) {
                    return new StringPadProcessLink("+");
                }
                return super.newProcessLink(clazz);
            }
        };
    }

    @Test
    public void testDefaultFactory() {
        ProcessLinkFactory factory = new DefaultProcessLinkFactory();
        ProcessLink link = factory.newProcessLink(StringIndentProcessLink.class);
        Assert.assertNotNull(link);
        Assert.assertTrue(link instanceof StringIndentProcessLink);
        //noinspection unchecked
        Assert.assertEquals(DEFAULT_EXPECTED, link.apply(TEST_STRING));
    }

    @Test
    public void testNonDefaultFactoryDefaultLink() {
        ProcessLinkFactory factory = extendedDefaultLinkFactory;
        ProcessLink link = factory.newProcessLink(StringIndentProcessLink.class);
        Assert.assertNotNull(link);
        Assert.assertTrue(link instanceof StringIndentProcessLink);
        //noinspection unchecked
        Assert.assertEquals(DEFAULT_EXPECTED, link.apply(TEST_STRING));
    }

    @Test
    public void testNonDefaultFactoryNonDefaultLink() {
        ProcessLinkFactory factory = extendedDefaultLinkFactory;
        ProcessLink link = factory.newProcessLink(StringPadProcessLink.class);
        Assert.assertNotNull(link);
        Assert.assertTrue(link instanceof StringPadProcessLink);
        //noinspection unchecked
        Assert.assertEquals(OTHER_EXPECTED, link.apply(TEST_STRING));
    }

    /**
     * {@link DefaultProcessLinkFactory} should give a new {@link ProcessLink} instance on each call to {@code newProcessLink}
     */
    @Test
    public void testNewLinkEachTime() {
        ProcessLinkFactory factory = new DefaultProcessLinkFactory();
        ProcessLink link1 = factory.newProcessLink(StringIndentProcessLink.class);
        ProcessLink link2 = factory.newProcessLink(StringIndentProcessLink.class);
        Assert.assertFalse(link1 == link2);
    }

    /**
     * {@link DefaultProcessLinkFactory} should throw {@link NoSuchMethodException} when requested {@link ProcessLink} does not have a default constructor.
     *
     * @throws Throwable
     */
    @Test(expected = NoSuchMethodException.class)
    public void testDefaultFactoryFailOnNonDefaultLink() throws Throwable {
        ProcessLinkFactory factory = new DefaultProcessLinkFactory();
        try {
            factory.newProcessLink(StringPadProcessLink.class);
        } catch (RuntimeException e) {
            throw e.getCause();
        }

    }
}
