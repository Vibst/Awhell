package com.socilanetworking.postservice.monitor.background;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.socilanetworking.postservice.service.LikeAndcommentService;
import com.socilanetworking.postservice.service.Postservice;

@Service
public class monitorService {

    Lock lock = new ReentrantLock();
    public Condition condtion = lock.newCondition();
    private Postservice postservice;

    public monitorService(Postservice postservice) {
        this.postservice = postservice;

    }

    public void DetectNode() {
        try {
            lock.lockInterruptibly();
            try {
                if (lock.tryLock(5, TimeUnit.SECONDS)) {
                    Runnable task = () -> {
                        boolean isActive = allReadFileIsActive();
                        if (isActive) {

                        }

                    };

                    postservice.getCountLikeId(task, true);

                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (Exception e) {

        }

    }

    private boolean allReadFileIsActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allReadFileIsActive'");
    }

}
