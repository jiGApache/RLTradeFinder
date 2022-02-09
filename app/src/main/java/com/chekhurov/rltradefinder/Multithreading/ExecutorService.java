package com.chekhurov.rltradefinder.Multithreading;

import java.util.concurrent.Executors;

public class ExecutorService {

    public static final String LOAD_POPULAR_ITEMS = "LOAD_POPULAR_ITEMS";
    public static final String LOAD_POPULAR_IMAGES = "LOAD_POPULAR_IMAGES";

    private final java.util.concurrent.ExecutorService executorService;

    private Runnable runnable;

    public ExecutorService(String whichRunnable){
        if (whichRunnable.equals(LOAD_POPULAR_ITEMS)) {
            int numberOfProcessors = Runtime.getRuntime().availableProcessors();
            executorService = Executors.newFixedThreadPool(numberOfProcessors);
        } else if (whichRunnable.equals(LOAD_POPULAR_IMAGES)) {
            executorService = Executors.newSingleThreadExecutor();
        } else
            executorService = null;
    }

    public void execute(Runnable runnable){
        this.runnable = runnable;
        executorService.execute(runnable);
    }

    public void stopRunnable(){

    }

}