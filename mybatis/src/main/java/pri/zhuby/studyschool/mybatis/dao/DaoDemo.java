package pri.zhuby.studyschool.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import pri.zhuby.studyschool.mybatis.SqlSessionInitFactory;
import pri.zhuby.studyschool.mybatis.dao.mapper.VendorMOTypeMapper;
import pri.zhuby.studyschool.mybatis.pojo.VendorMOType;

/**
 * 实现查询
 * Created by zhuby on 2016/5/17.
 */
public class DaoDemo {

    /**
     * 在代码中指定namespace + statementId 的方式执行查询
     * @return
     */
    public String queryMoTypeNameWithNamespace(String motypeid){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance(SqlSessionInitFactory.INIT_TYPE_DEFAULT ).
                    getSqlSessionFactory().openSession();
        try {
            // mapper xml文件中的namespace + statementId
            VendorMOType vendorMOType = sqlSession.selectOne( "pri.zhuby.studyschool.mybatis.dao.mapper.VendorMOTypeMapper.selectVendorMOType",
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
     * 使用mapper Interface的方式实现查询，建议的使用方式
     * @param motypeid
     * @return
     */
    public String queryMoTypeNameWithMapper(String motypeid){
        SqlSession sqlSession = SqlSessionInitFactory.getInstance(SqlSessionInitFactory.INIT_TYPE_DEFAULT ).
                getSqlSessionFactory().openSession();

        try{
            VendorMOTypeMapper vendorMoTypeMapper = sqlSession.getMapper( VendorMOTypeMapper.class );
            VendorMOType vendorMOType = vendorMoTypeMapper.getMOTypeNameWithMapper( motypeid  );
            if (vendorMOType != null) {
                return vendorMOType.getMotypeName();
            }
        }finally {
            sqlSession.close();
        }

        return null;
    }

}
