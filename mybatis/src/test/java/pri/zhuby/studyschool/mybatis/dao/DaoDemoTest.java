package pri.zhuby.studyschool.mybatis.dao;

import junit.framework.TestCase;

/**
 * DaoDemo的测试类
 * Created by zhuby on 2016/5/17.
 */
public class DaoDemoTest extends TestCase {
    private DaoDemo daoDemo = new DaoDemo();

    public void testQueryMoTypeNameWithNamespace() throws Exception {
        String moTypeId = "2a7e32a9d94940d89a30449331524096";
        String expectMoTypeName = "NE Device";
        assertEquals( daoDemo.queryMoTypeNameWithNamespace( moTypeId ), expectMoTypeName );
    }

    public void testQueryMoTypeNameWithMapper() throws Exception {
        String moTypeId = "2a7e32a9d94940d89a30449331524096";
        String expectMoTypeName = "NE Device";
        assertEquals( daoDemo.queryMoTypeNameWithMapper( moTypeId ), expectMoTypeName );
    }
}