package net.servokio.q4mc.threads;

import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
    public List<Thread> threads;

    public ThreadManager(){
        this.threads = new ArrayList<>();
    }

    public void addThread(Thread thread){
        this.threads.add(thread);
    }

    public void stopAll(){
        for(Thread t: threads) t.interrupt();
    }
}
