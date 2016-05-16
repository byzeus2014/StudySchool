package pri.zhuby.studyschool.c3p0.connimpl;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1.C3P0的学习程序
 * 2.使用了单态模式，注释中有单态模式的使用注意事项。
 * Created by zhuby on 2016/5/4.
 */
public class ConfigConnectionPool {
    private ComboPooledDataSource ds = null;
    private static ConfigConnectionPool connectionPool= null;
    // 文件名必须是 c3p0.properties，指定使用这个配置文件中特殊的配置。
    private String propertyFileName = "myc3p0";

    //单态模式，一般是private的构造方法
    //使用了 “懒实例化”的方式，第一次使用的时候，会实例化。
    private ConfigConnectionPool(){
        ds = new ComboPooledDataSource( propertyFileName );
    }

    // synchronized 关键字必须得增加，否则在多线程并发的情况下，会实例化出多个实例
    public static synchronized ConfigConnectionPool getInstance(){
        if(connectionPool == null){
            return (new ConfigConnectionPool());
        }else{
            return connectionPool;
        }
    }

    public Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getConnPoolURL(){
        StringBuffer connDetail = new StringBuffer();
        connDetail.append("URL: "+ds.getJdbcUrl());
        return connDetail.toString();
    }

    public void printConnPoolDetail( ){
        System.out.println("URL: "+ds.getJdbcUrl());
        System.out.println("maxPoolSize: "+ds.getMaxPoolSize());
        System.out.println("maxIdelTime: "+ds.getMaxIdleTime());

        try {
            System.out.println("NumConnectionsAllUsers: "+ds.getNumConnectionsAllUsers());
            System.out.println("NumConnectionsDefaultUser: "+ ds.getNumConnectionsDefaultUser() );
            System.out.println("NumConnections: "+ ds.getNumConnections() );
            System.out.println("NumUserPools: "+ds.getNumUserPools());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  main(String[] args){
        ConfigConnectionPool pool = ConfigConnectionPool.getInstance();
        pool.printConnPoolDetail();
    }

}
