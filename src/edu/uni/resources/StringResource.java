package edu.uni.resources;


import edu.uni.Common;



public class StringResource extends Resource {


    private String[] stringResource;


    public StringResource(String val) {
        this.stringResource = val.trim().split(" ");
    }
    


    @Override
    public boolean processNext() {


        int value;
        if (counter < stringResource.length) {

            try {
                value = Integer.parseInt(stringResource[counter++]);
                if (Common.isMatched(value)) {
                    synchronized (Common.class) {
                        Common.sum += value;
                        System.out.println(Common.sum);
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Unexpected value in resource. Stopping...");
                this.finished = true;
                return false;
            }

        } else {
            this.finished = true;
        }

        return true;

    }


}
