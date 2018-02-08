package edu.uni.handler;


import edu.uni.resources.IResource;

public class ResourceHandler implements Runnable {

    private IResource res;
    private static volatile boolean stopAll = false;


    public ResourceHandler(IResource res) {
        this.res = res;
    }

    @Override
    public void run() {

        while (!stopAll && !res.isFinished()) {

            if (!res.processNext()) {
                stopAll = true;
                break;
            }

        }


    }

}
