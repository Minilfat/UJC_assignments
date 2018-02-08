package edu.uni.handler;


public class ResourceHandler implements Runnable {

    private Resource res;
    private static volatile boolean stopAll = false;


    public ResourceHandler(Resource res) {
        this.res = res;
    }

    @Override
    public void run() {

        while (!stopAll && !res.finished) {

            if (!res.processNext()) {
                stopAll = true;
                break;
            }

        }


    }

}
