package pri.zhuby.studyschool.thread.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试往线程池中添加任务超过线程数时,
 * 不同线程池的处理策略
 * @author zhuby
 *
 */
public class ThreadPoolTest {
    
	/**
	 * API中提供的FixedThreadPool
	 */
    public void fixedThreadPoolTest() {
    	int listSize = 8 ;
    	int threadNumber = 2;
    	List<String> nameList = generateNameList(listSize);
    	
    	try {
    		ExecutorService es = generateFixedThreadPool(threadNumber);
    		
	    	excuteThreads(es, nameList);
    	} catch (Exception e2){
    		e2.printStackTrace();
    	}
	}

    /**
     * BlockingQueue之SynchronousQueue
     *
     * 执行结果:  直接报异常
     * Test fixed thread pool with synchronizeQueue start...
	 * main : new Simple thread, name is N1
	 * main : new Simple thread, name is N2
	 * pool-1-thread-1RUN, name is N1
	 * pool-1-thread-2RUN, name is N2
	 * main : new Simple thread, name is N3
	 * java.util.concurrent.RejectedExecutionException: Task Thread[Thread-2,5,main] rejected from java.util.concurrent.ThreadPoolExecutor@70dea4e[Running, pool size = 2, active threads = 0, queued tasks = 0, completed tasks = 2]
	 * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
	 * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
	 * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
	 * 	at ThreadPoolTest.excuteThreads(ThreadPoolTest.java:114)
	 * 	at ThreadPoolTest.fixedThreadPoolwithSynchronousQueue(ThreadPoolTest.java:52)
	 * 	at ThreadPoolTest.main(ThreadPoolTest.java:136)
	 * Test fixed thread pool with synchronizeQueue end.
	 *
	 * --------------
	 * 分析:
	 * SynchronousQueue是直接提交队列, 线程池大小为2,
	 * 所以, 只有两个任务提交后正确执行了, 第三个任务生成后, 再添加时就抛出异常了.
     */
    public void fixedThreadPoolwithSynchronousQueue() {
    	int listSize = 8 ;
    	int threadNumber = 2;
    	List<String> nameList = generateNameList(listSize);
       	
    	try {
    		ExecutorService es = generateFixedThreadPoolWithSynchronizeQueue(threadNumber);
    		
	    	excuteThreads(es, nameList);
	    	
    	} catch (Exception e2){
    		e2.printStackTrace();
    	}
	}
    
    /**
     * BlockingQueue之LinkedBlockingQueue 
     * 程序正确执行, 实现方式与API中提供的FixedThreadPool机制相同.
     * 分析:
     * LinkedBlockingQueue 是无界队列, 所有的待执行任务都会添加到该队列中.
     */
    public void fixedThreadPoolwithLinkedBlockingQueue() {
    	int listSize = 8 ;
    	int threadNumber = 2;
    	List<String> nameList = generateNameList(listSize);
    	
    	try {
    		ExecutorService es = generateFixedThreadPoolWithLinkedBlockingQueue(threadNumber);
    		
    		excuteThreads(es, nameList);
    		
    	} catch (Exception e2){
    		e2.printStackTrace();
    	}
    }
    
    /**
     * BlockingQueue之ArrayBlockingQueue 
     * 
     * 执行结果: 抛出异常, 接受的任务正常执行; 无法加入队列后, 直接抛出异常.
     * Test fixed thread pool with ArrayBlockingQueue start...
     * main : new Simple thread, name is N1
     * main : new Simple thread, name is N2
     * main : new Simple thread, name is N3
     * main : new Simple thread, name is N4
     * main : new Simple thread, name is N5
     * main : new Simple thread, name is N6
     * java.util.concurrent.RejectedExecutionException: Task Thread[Thread-5,5,main] rejected from java.util.concurrent.ThreadPoolExecutor@4e25154f[Running, pool size = 2, active threads = 2, queued tasks = 3, completed tasks = 0]
     * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
     * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
     * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
     * 	at ThreadPoolTest.excuteThreads(ThreadPoolTest.java:210)
     * 	at ThreadPoolTest.fixedThreadPoolwithArrayBlockingQueue(ThreadPoolTest.java:112)
     * 	at ThreadPoolTest.main(ThreadPoolTest.java:252)
     * Test fixed thread pool with ArrayBlockingQueue end.
     * -----------------------------------------
     * pool-1-thread-1RUN, name is N1
     * pool-1-thread-2RUN, name is N2
     * pool-1-thread-1RUN, name is N3
     * pool-1-thread-2RUN, name is N4
     * pool-1-thread-1RUN, name is N5
	 * 
	 * ------
	 * 分析:
	 * ArrayBlockingQueue是有界队列. 线程池大小为2, 队列大小为3 , 使用默认的handler: AbortPolicy
	 * 所以, 执行中两个线程的任务, 队列中的3个任务 成功执行, 其他的新任务则直接拒绝执行, 抛出异常.
     */
    public void fixedThreadPoolwithArrayBlockingQueue() {
    	int listSize = 8 ;
    	int threadNumber = 2;
    	int queueSize = 3;
    	List<String> nameList = generateNameList(listSize);
    	
    	try {
    		ExecutorService es = generateFixedThreadPoolWithArrayBlockingQueue(threadNumber, queueSize);
    		
    		excuteThreads(es, nameList);
    		
    	} catch (Exception e2){
    		e2.printStackTrace();
    	}
    }
    /**
     * BlockingQueue之ArrayBlockingQueue , 使用的是 CallerRunHandler.
     * 
     * 执行结果:
     * Test fixed thread pool with ArrayBlockingQueue & CallerRunHandler start...
     * main : new Simple thread, name is N1
     * main : new Simple thread, name is N2
     * main : new Simple thread, name is N3
     * main : new Simple thread, name is N4
     * main : new Simple thread, name is N5
     * main : new Simple thread, name is N6
     * pool-1-thread-1 RUN, name is N1
     * main RUN, name is N6
     * main : new Simple thread, name is N7
     * pool-1-thread-2 RUN, name is N2
     * pool-1-thread-2 RUN, name is N3
     * pool-1-thread-1 RUN, name is N4
     * main RUN, name is N7
     * main : new Simple thread, name is N8
     * main : new Simple thread, name is N9
     * main : new Simple thread, name is N10
     * main : new Simple thread, name is N11
     * main : new Simple thread, name is N12
     * pool-1-thread-2 RUN, name is N5
     * pool-1-thread-1 RUN, name is N8
     * main RUN, name is N12
     * main : new Simple thread, name is N13
     * main : new Simple thread, name is N14
     * main : new Simple thread, name is N15
     * pool-1-thread-2 RUN, name is N9
     * main RUN, name is N15
     * main : new Simple thread, name is N16
     * main : new Simple thread, name is N17
     * pool-1-thread-1 RUN, name is N10
     * pool-1-thread-2 RUN, name is N11
     * pool-1-thread-1 RUN, name is N13
     * main RUN, name is N17
     * main : new Simple thread, name is N18
     * main : new Simple thread, name is N19
     * main : new Simple thread, name is N20
     * pool-1-thread-2 RUN, name is N14
     * pool-1-thread-1 RUN, name is N16
     * Test fixed thread pool with ArrayBlockingQueue & CallerRunHandler end.
     * -----------------------------------------
     * pool-1-thread-2 RUN, name is N18
     * pool-1-thread-1 RUN, name is N19
     * pool-1-thread-2 RUN, name is N20
     * 
     * 分析:
     * ArrayBlockingQueue是有界队列. 线程池大小为2, 队列大小为3 , 使用默认的handler: CallerRunHandler
     * 当线程和队列中都满了后,使用主进程来执行线程任务, 即任务6 是由Main来执行的.  "main RUN, name is N6" 
     */
	private void fixedThreadPoolwithArrayBlockingQueueCallerRunHandler() {
    	int listSize = 20 ;
    	int threadNumber = 2;
    	int queueSize = 3;
    	List<String> nameList = generateNameList(listSize);
    	
    	try {
    		ExecutorService es = generateFixedThreadPoolWithArrayBlockingQueueCallerRunHandler(threadNumber, queueSize);
    		
    		excuteThreads(es, nameList);
    		
    	} catch (Exception e2){
    		e2.printStackTrace();
    	}
    }
    
    /**
     * 生成一个固定线程数的线程池, 但是BlockQueue是SynchronizeQueue
     * SynchronousQueue 
     * 直接提交队列
     */
	private ExecutorService generateFixedThreadPoolWithSynchronizeQueue(int threadNumber) {
		int corePoolSize = threadNumber;
        int maximumPoolSize = threadNumber;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.MICROSECONDS;
        BlockingQueue<Runnable> workQueue = new SynchronousQueue<>() ;
        RejectedExecutionHandler handler  = null ;   // 使用默认的handler

		return new ThreadPoolExecutor(
             corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
    
    /**
     * 生成一个固定线程数的线程池, 但是BlockQueue是LinkedBlockingQueue
     * LinkedBlockingQueue 
     * 是无界队列
     */
	private ExecutorService generateFixedThreadPoolWithLinkedBlockingQueue(int threadNumber) {
		int corePoolSize = threadNumber;
        int maximumPoolSize = threadNumber;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.MICROSECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>() ;
        RejectedExecutionHandler handler  = null ;   // 使用默认的handler

		return new ThreadPoolExecutor(
             corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	/**
	 * 生成一个固定线程数的线程池, 但是BlockQueue是ArrayBlockingQueue
	 * ArrayBlockingQueue 
	 * 是有界队列
	 */
	private ExecutorService generateFixedThreadPoolWithArrayBlockingQueue(int threadNumber, int queueSize) {
		int corePoolSize = threadNumber;
		int maximumPoolSize = threadNumber;
		long keepAliveTime = 0;
		TimeUnit unit = TimeUnit.MICROSECONDS;
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueSize) ;
		RejectedExecutionHandler handler  = null ;   // 使用默认的handler

		return new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	/**
	 * 
	 * @param threadNumber
	 * @param queueSize
	 * @return
	 */
	private ExecutorService generateFixedThreadPoolWithArrayBlockingQueueCallerRunHandler(int threadNumber, int queueSize) {
		int corePoolSize = threadNumber;
		int maximumPoolSize = threadNumber;
		long keepAliveTime = 0;
		TimeUnit unit = TimeUnit.MICROSECONDS;
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueSize) ;
		RejectedExecutionHandler handler  = new ThreadPoolExecutor.CallerRunsPolicy();   // 使用默认的handler

		return new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,handler);
	}

	
    /**
     * 构造一个NameList, 通过这个List生成一组线程.
     * @param listSize
     * @return
     */
	private List<String> generateNameList(int listSize) {
		List<String> nameList = new ArrayList<String>(listSize);
    	for(int i=0;i< listSize ;i++){
    		nameList.add( "N"+(i+1));
    	}
		return nameList;
	}
    /**
     * 构造一个FixedThreadPool
     * @return
     */
	private ExecutorService generateFixedThreadPool(int threadNumber) {
		return Executors.newFixedThreadPool(threadNumber);
	}
	
	/**
	 * 根据Name List生成一组线程, 使用ExecutorService执行
	 * @param nameList  线程的name列表
	 * @param es    线程池执行器
	 * @throws InterruptedException
	 */
	private void excuteThreads(ExecutorService es, List<String> nameList)
			throws InterruptedException {
		try{
			for (String aNameList : nameList) {
				es.execute( new SimpleThread( aNameList ) );
			}
			
			es.shutdown();
			
			while(true){
				Thread.sleep(1000);
				if(es.isShutdown() || es.isTerminated()){
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			es.shutdown();		// 如果不加这个, 在 es.execute时抛出异常, 线程池不会退出(es.shutdown()这句执行不到). 
		}
	}

    
	public static void main(String[] args){
			    
	    ThreadPoolTest threadPoolTest = new ThreadPoolTest();
//	    System.out.println("Test fixed thread pool start...");
//	    threadPoolTest.fixedThreadPoolTest();
//	    System.out.println("Test fixed thread pool end.");
//	    System.out.println("-----------------------------------------");
//	    
//	    try{
//	    	System.out.println("Test fixed thread pool with synchronizeQueue start...");
//	    	threadPoolTest.fixedThreadPoolwithSynchronousQueue();
//	    	System.out.println("Test fixed thread pool with synchronizeQueue end.");
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
//	    
//	    System.out.println("-----------------------------------------");
//	    try{
//		   System.out.println("Test fixed thread pool with LinkedBlockingQueue start...");
//		   threadPoolTest.fixedThreadPoolwithLinkedBlockingQueue();
//		   System.out.println("Test fixed thread pool with LinkedBlockingQueue end.");
//	    }catch(Exception e){
//		   e.printStackTrace();
//	    }
//	    System.out.println("-----------------------------------------");
//	    	    
//	    try{
//	    	System.out.println("Test fixed thread pool with ArrayBlockingQueue start...");
//	    	threadPoolTest.fixedThreadPoolwithArrayBlockingQueue();
//	    	System.out.println("Test fixed thread pool with ArrayBlockingQueue end.");
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
//	    System.out.println("-----------------------------------------");
	    
	    try{
	    	System.out.println("Test fixed thread pool with ArrayBlockingQueue & CallerRunHandler start...");
	    	threadPoolTest.fixedThreadPoolwithArrayBlockingQueueCallerRunHandler();
	    	System.out.println("Test fixed thread pool with ArrayBlockingQueue & CallerRunHandler end.");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    System.out.println("-----------------------------------------");
		
	}
		
}
