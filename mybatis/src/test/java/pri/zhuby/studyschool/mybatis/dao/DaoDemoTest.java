package pri.zhuby.studyschool.mybatis.dao;

import junit.framework.TestCase;

/**
 * DaoDemo的测试类
 * Created by zhuby on 2016/5/17.
 */
public class DaoDemoTest extends TestCase {
    private DaoDemo daoDemo = new DaoDemo();

    /**
     * 测试使用namespace+statementid的方式查询
     * @throws Exception
     */
    public void testQueryMoTypeNameWithNamespace() throws Exception {
        String moTypeId = "2a7e32a9d94940d89a30449331524096";
        String expectMoTypeName = "NE Device";
        assertEquals( daoDemo.queryMoTypeNameWithNamespace( moTypeId ), expectMoTypeName );
    }

    /**
     * 测试使用mapper类的方式查询
     * @throws Exception
     */
    public void testQueryMoTypeNameWithMapper() throws Exception {
        String moTypeId = "2a7e32a9d94940d89a30449331524096";
        String expectMoTypeName = "NE Device";
        assertEquals( daoDemo.queryMoTypeNameWithMapper( moTypeId ), expectMoTypeName );
    }

    public void testCounterVendorMoType() throws Exception {
        int expectNumber = 54 ;
        assertEquals( daoDemo.counterVendorMoType(), expectNumber );
    }

    public void testCounterVendorMoTypeWithProperty() throws Exception {
        int expectNumber = 59 ;
        assertEquals( daoDemo.counterVendorMoTypeWithProperty(), expectNumber );
    }
}