package pri.zhuby.studyschool.mybatis.dao.mapper;

import org.apache.ibatis.annotations.Param;
import pri.zhuby.studyschool.mybatis.pojo.VendorMOTypePojo;

/**
 * 使用Mapper Interface的方式进行查询
 * Created by zhuby on 2016/5/17.
 */
public interface VendorMOTypeMapper {

    //建议使用注解：指定对应了SQL中哪个参数；在多个参数的场景下非常有用
    //方法名称（getMOTypeNameWithMapper）必须与 mapper xml文件中的 sql statementid 相同
    VendorMOTypePojo getMOTypeNameWithMapper(@Param("motypeidInSql") String motypeid);

    int countVendorMoType();
}
