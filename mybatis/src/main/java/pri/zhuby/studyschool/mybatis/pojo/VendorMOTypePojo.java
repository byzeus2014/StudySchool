package pri.zhuby.studyschool.mybatis.pojo;

/**
 * POJO对象，对应Mdl_Vendormotype表
 * 注意：
 *   对象中的属性字段，必须与数据库字段一致！
 *   否则查询无法对其赋值！
 *  即，以下示例中，数据库中字段名称必须有
 *      MOTYPEID 和 MOTYPENAME
 *    否则查询结果中，这些字段值为null
 * Created by zhuby on 2016/5/17.
 */
public class VendorMOTypePojo {

    private String motypeid;
    private String motypeName;

    //如果使用motypeNameTest这个名称的属性，
    //则查询结果中无法对其赋值。
    //private String motypeNameTest;


    public String getMotypeid() {
        return motypeid;
    }

    public String getMotypeName() {
        return motypeName;
    }

    public void setMotypeid(String motypeid) {
        this.motypeid = motypeid;
    }

    public void setMotypeName(String motypeName) {
        this.motypeName = motypeName;
    }
}
