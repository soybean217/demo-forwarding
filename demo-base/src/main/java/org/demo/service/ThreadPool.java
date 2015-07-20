package org.demo.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	static public ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(3, 30, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(30000), new ThreadPoolExecutor.DiscardOldestPolicy());

}
