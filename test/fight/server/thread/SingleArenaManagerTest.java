package fight.server.thread;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class SingleArenaManagerTest {

    private static ApplicationContext ac;

    public SingleArenaManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ac = new FileSystemXmlApplicationContext("./config/app.xml");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testJoinSingleArena() {
    }
}