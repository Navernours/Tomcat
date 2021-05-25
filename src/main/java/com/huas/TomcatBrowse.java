package com.huas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class TomcatBrowse {
    private static int port = 5228;
    private static UrlUtil urlUtil = new UrlUtil();
    public static void main(String[] args) {
        System.out.println("My Tomcat is Running");
        try  {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();// 服务器每接受一次请求，就创建一个socket对象
                InputStream in = socket.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(in));
                String info = null;
                String infoline = br.readLine();
                while (infoline != null) {
                    info =info+infoline;
                    infoline = br.readLine();
                }
                UrlBean url = urlUtil.readString(info);
                if (url != null) {
                    String path=url.getPath();
                    String className = url.getFileName();
                    String methodName = url.getParameter().trim();
                    ClassLoader classloader=ClassLoader.getSystemClassLoader();
                    try {
                        classloader.loadClass(path+"."+className);
                        Class<?> getclass=Class.forName(path+"."+className);
                        Method method=getclass.getMethod(methodName, null);
                        method.invoke(getclass.newInstance(), null);

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {

                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
