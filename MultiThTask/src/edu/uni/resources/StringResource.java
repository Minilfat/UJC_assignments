package edu.uni.resources;




public class StringResource extends Resource {


    public StringResource(String val) {
        values = val.trim().split("\\s+");
    }


    @Override
    public boolean processNext() {

        if (counter >= values.length)
            return finished = true;

        return add();

    }


}
