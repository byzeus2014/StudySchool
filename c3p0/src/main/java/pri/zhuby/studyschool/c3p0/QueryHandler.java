package pri.zhuby.studyschool.c3p0;

import pri.zhuby.studyschool.c3p0.connimpl.ConfigConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 执行SQL
 *
 * Created by zhuby on 2016/5/4.
 */
public class QueryHandler {
    private ConfigConnectionPool connPool = ConfigConnectionPool.getInstance();

    public String queryOne( ){
        Connection conn = connPool.getConnection();
        String sysdate = null;
        String sql = "select count(1) from pm4h_mo.mdl_resmodel t";
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            while(rs.next()){
                sysdate=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(pstat!=null){
                    pstat.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sysdate;
    }

    public boolean updateOne(  ){
        Connection conn = connPool.getConnection();

        String sql = "update pm4h_ad.test_tab t set t.name='new name' where t.id = '111'";
        PreparedStatement pstat = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            pstat = conn.prepareStatement(sql);
            result = pstat.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(pstat!=null){
                    pstat.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public  static void main(String[] args){
        QueryHandler qh = new QueryHandler();
        System.out.println("query test : ");
        System.out.println(  qh.queryOne() );


        System.out.println("update test");
        //用来测试 debug连接泄露
        // maxIdleTime=60
        // unreturnedConnectionTimeout= 70
        // 正常情况下，一条SQL执行完，最大空闲60s就会被回收，假设SQL执行都小于10s，当sql超过70s还没有回收的话，
        // 说明该连接有问题。
        // debugUnreturnedConnectionStackTraces配置了这个参数后，就会把连接的堆栈打印出来。
        qh.updateOne();
        qh.connPool.printConnPoolDetail();
        System.out.println("end");
    }
}
