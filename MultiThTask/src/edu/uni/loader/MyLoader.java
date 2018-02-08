package edu.uni.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyLoader extends ClassLoader {
    private static final String LIB_PATH = "/Users/ilfat/IdeaProjects/UJC_assignments/MultiThTask/lib/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {

            String path = LIB_PATH + name.replace(".", "/") + ".class";
            InputStream is = new FileInputStream(new File(path));


            byte[] classBytes = new byte[is.available()];
            if (is.read(classBytes) != classBytes.length) {
                throw new IOException("No....");
            }


            // Close the input stream and return bytes
            is.close();

            return defineClass(name, classBytes, 0, classBytes.length);

        } catch (IOException e) {
            throw new ClassNotFoundException(e.getMessage(), e);
        }
    }

}
