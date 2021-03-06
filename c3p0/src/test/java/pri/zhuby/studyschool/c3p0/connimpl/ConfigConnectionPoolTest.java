package pri.zhuby.studyschool.c3p0.connimpl;

import junit.framework.TestCase;

/**
 * ConfigConnectionPool的测试类
 * Created by zhuby on 2016/5/16.
 */
public class ConfigConnectionPoolTest extends TestCase {

    public void testGetConnection() throws Exception {
        assertNotNull(ConfigConnectionPool.getInstance().getConnection());
    }

    /**
     * 测试获取的connection是否是 指定的配置
     * 默认配置是
     *          URL: jdbc:oracle:thin:@//10.110.2.104:1522/acrosspm
     *  myc3p0的配置是
     *          URL: jdbc:oracle:thin:@//10.110.2.103:1522/acrosspm
     * @throws Exception
     */
    public void testGetConnPoolDetail() throws Exception {
        assertEquals(ConfigConnectionPool.getInstance().getConnPoolURL(),
                "URL: jdbc:oracle:thin:@//10.110.2.103:1522/acrosspm");
    }

}