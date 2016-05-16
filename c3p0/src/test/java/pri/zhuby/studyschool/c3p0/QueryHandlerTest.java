package pri.zhuby.studyschool.c3p0;

import junit.framework.TestCase;

/**
 * QueryHandler的测试类
 * Created by zhuby on 2016/5/16.
 */
public class QueryHandlerTest extends TestCase {

    private QueryHandler queryHandler = null;

    /**
     * 构造函数
     */
    public QueryHandlerTest() {
        this.queryHandler = new QueryHandler();
    }


    public void testQueryOne() throws Exception {

        assertNotNull(queryHandler.queryOne());
    }

    public void testUpdateOne() throws Exception {
        assertTrue(queryHandler.updateOne());
    }


}