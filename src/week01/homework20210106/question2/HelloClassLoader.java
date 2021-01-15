package week01.homework20210106.question2;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author exercise -- chengyan
 * @date 2021/1/15 00:28
 **/
public class HelloClassLoader extends ClassLoader{

    private final static String PATH = "src/week01/homework20210106/question2/";

    private final static String SUFFIX = ".xlass";

    public static void main(String[] args){

        try {

            Object o = new HelloClassLoader().findClass("Hello").newInstance();

            Class<?> classType = o.getClass();

            Method helloMethod = classType.getDeclaredMethod("hello");

            helloMethod.invoke(o);

        } catch (IllegalAccessException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        byte[] bytes = new byte[0];

        File classFile = new File(PATH + name + SUFFIX);

        if (classFile.exists()) {

            try (InputStream in = new FileInputStream(classFile);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                int byteValue;

                while ((byteValue = in.read()) != -1) {
                    out.write(255 - byteValue);
                }

                bytes = out.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return defineClass(name , bytes, 0, bytes.length);

    }


}
