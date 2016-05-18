package pri.zhuby.studyschool.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import pri.zhuby.studyschool.mybatis.SqlSessionInitFactory;
import pri.zhuby.studyschool.mybatis.dao.mapper.VendorMOTypeMapper;
import pri.zhuby.studyschool.mybatis.pojo.VendorMOTypePojo;

/**
 * 实现查询
 * 1. 在代码中指定namespace + statementId
 * 2. 使用Mapper Interface的方式查询
 *    2.1 可以在Mapper Interface中使用注解的方式，（sql写到注解中）但只支持简单的SQL。
 *    2.2 可以把Sql写到Mapper XML文件中，Mapper Interface中只定义查询sql方法名称 （推荐使用！！！）
 * Created by zhuby on 2016/5/17.
 */
public class DaoDemo {

    /**
     * 1.在代码中指定namespace + statementId 的方式执行查询,
     *  不推荐使用，因为容易出现字符串引起的错误，写代码也不高效
     * @return
     */
    public String queryMoTypeNameWithNamespace(String motypeid){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance(SqlSessionInitFactory.INIT_TYPE_DEFAULT ).
                    getSqlSessionFactory().openSession();
        try {
            // mapper xml文件中的namespace + statementId
            VendorMOTypePojo vendorMOType = sqlSession.
                    selectOne( "pri.zhuby.studyschool.mybatis.dao.mapper.VendorMOTypeMapper.selectVendorMOType",
                                motypeid );
            if (vendorMOType != null) {
                return vendorMOType.getMotypeName();
            }
        } finally {
            //！！！ 必须在finally中添加关闭
            sqlSession.close();
        }
        return null;
    }

    /**
     * 2.使用mapper Interface的方式实现查询，
     * 建议的使用方式，因为这样可以减少出错！
     * @param motypeid
     * @return
     */
    public String queryMoTypeNameWithMapper(String motypeid){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance(SqlSessionInitFactory.INIT_TYPE_DEFAULT ).
                getSqlSessionFactory().openSession();

        try{
            VendorMOTypeMapper vendorMoTypeMapper = sqlSession.getMapper( VendorMOTypeMapper.class );
            VendorMOTypePojo vendorMOType = vendorMoTypeMapper.getMOTypeNameWithMapper( motypeid  );
            if (vendorMOType != null) {
                return vendorMOType.getMotypeName();
            }
        }finally {
            sqlSession.close();
        }
        return null;
    }

    /**
     * 练习用的，额外写的一个Demo
     * @return
     */
    public int counterVendorMoType(){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance( SqlSessionInitFactory.INIT_TYPE_DEFAULT ).
                getSqlSession();
        try {
            VendorMOTypeMapper mapper = sqlSession.getMapper( VendorMOTypeMapper.class );
            int number = mapper.countVendorMoType();
            return number;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试使用property的sqlsessionfactory
     * @return
     */
    public int counterVendorMoTypeWithProperty(){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance( SqlSessionInitFactory.INIT_TYPE_PROPERTY ).
                getSqlSession();
        try {
            VendorMOTypeMapper mapper = sqlSession.getMapper( VendorMOTypeMapper.class );
            return mapper.countVendorMoType();
        } finally {
            sqlSession.close();
        }
    }

}
