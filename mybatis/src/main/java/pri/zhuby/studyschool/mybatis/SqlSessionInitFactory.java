package pri.zhuby.studyschool.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SqlSessionFactory是Mybatis的核心，
 * 作用域应该是整个应用，
 * 用它来获取sqlsession来执行SQL。
 *
 * 这个类用来初始化SQLSessionFactory，
 * 初始化有两种方式：
 * 1）读取配置文件——推荐方式
 * 2）用JavaBean自己生成。
 * Created by zhuby on 2016/5/17.
 */
public class SqlSessionInitFactory {
    private static SqlSessionFactory sqlSessionFactory = null;
    public static final int INIT_TYPE_DEFAULT = 0;
    public static final int INIT_TYPE_PROPERTY = 1;
    public static final int INIT_TYPE_JAVABEAN = 2;

    private static SqlSessionInitFactory sqlSessionInitFactory = null;  //单态模式

    /**
     * 统一的构造函数，根据输入的类型，构造不同的sqlsessionfactory出来。
     *
     * @param initType
     */
    private SqlSessionInitFactory(int initType){

        switch (initType){
            case INIT_TYPE_DEFAULT:
                initSqlSessionFactory();
                break;
            case INIT_TYPE_PROPERTY:
                initSqlSessionFactoryByProperty();
                break;
            case INIT_TYPE_JAVABEAN:
                //TODO 补充javabean的实现机制
                break;
            default:
                initSqlSessionFactory();
        }
    }

    /**
     * 使用默认的参数构造sqlsessionfactory
     */
    private void initSqlSessionFactory(){
        String xmlConfigFileString = "conf/mybatis-config.xml";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream( xmlConfigFileString );
            sqlSessionFactory = new SqlSessionFactoryBuilder().build( is );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从另一个配置文件中读取出property，
     * 用来动态填充mybatis配置文件中的变量。
     * 一般用来把mybatis配置文件中DataSource里面的用户名、密码、url、driver等拆分到单独的property文件中。
     */
    private void initSqlSessionFactoryByProperty(){
        String xmlConfigFileString = "conf/mybatis-config-property.xml";
        String configPropertiesFileString = "conf/mybatis-datasource.properties";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream( xmlConfigFileString );
            Properties properties = Resources.getResourceAsProperties( configPropertiesFileString );
            // TODO 补充Property方式的注释
            sqlSessionFactory = new SqlSessionFactoryBuilder().build( is, properties );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单态模式，
     * 带有参数的单态模式 是有问题的！！！
     *
     * 比如，在程序中已经获取了一个inittype=0的实例，
     * 后面在重新调用获取一个initype=1的实例，
     * 第一次获取到的实例应该就改变了。
     *
     * 这应该是单态模式的一种误区！后面看看怎么改成工厂模式。
     * @param initType
     * @return
     */
    public static SqlSessionInitFactory getInstance(int initType){
        if (sqlSessionInitFactory == null) {
            sqlSessionInitFactory = new SqlSessionInitFactory( initType );
        }
        return sqlSessionInitFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
