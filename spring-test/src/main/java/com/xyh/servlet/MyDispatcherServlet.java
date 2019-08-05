package com.xyh.servlet;

import com.xyh.annotation.MyAutowired;
import com.xyh.annotation.MyController;
import com.xyh.annotation.MyRequestMapping;
import com.xyh.annotation.MyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/5
 */
public class MyDispatcherServlet extends HttpServlet {
    public static final long serialVersionUID=1L;

    public static final String LOCATION="contextConfigLocation";

    //保存所有的配置信息
    private Properties p=new Properties();
    //保存所有扫描到的类
    private List<String> classNames=new ArrayList<>();
    //保存所有初始化的bean
    private Map<String,Object> ioc=new HashMap<>();
    //保存所有的url与方法的映射关系
    private Map<String, Method> handlerMapping =new HashMap<>();

    public MyDispatcherServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter(LOCATION));
        //2.扫描所有相关的类
        doScanner(p.getProperty("scanPackage"));
        //3.初始化所有类的实例，保存到ioc容器
        doInstance();
        //4.依赖注入
        doAutowired();
        //5.构造handlerMapping
        initHandlerMapping();
        //6.等待请求，匹配url，定位方法，反射调用执行
        System.out.println("my mvc framework is init");
    }

    private void initHandlerMapping() {
        if(ioc.isEmpty())return;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(MyController.class))continue;
            String baseUrl="";
            if(clazz.isAnnotationPresent(MyRequestMapping.class)){
                MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                baseUrl=requestMapping.value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if(!method.isAnnotationPresent(MyRequestMapping.class))continue;
                MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                handlerMapping.put(url,method);
                System.out.println("mapped  "+url+","+method);
            }
        }
    }

    private void doAutowired() {
        if(ioc.isEmpty())return;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if(!field.isAnnotationPresent(MyAutowired.class)){
                    continue;
                }
                MyAutowired autowired = field.getAnnotation(MyAutowired.class);
                String beanName = autowired.value().trim();
                if("".equals(beanName)){
                    beanName=field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void doInstance() {
        if(classNames.size()==0)return;
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(MyController.class)){
                    String beanName = lowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,clazz.newInstance());
                }else if(clazz.isAnnotationPresent(MyService.class)){
                    MyService annotation = clazz.getAnnotation(MyService.class);
                    String beanName = annotation.value();
                    if(!"".equals(beanName.trim())){
                        ioc.put(beanName,clazz.newInstance());
                        continue;
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        ioc.put(anInterface.getName(),clazz.newInstance());
                    }
                }else{
                    continue;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File dir  = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                classNames.add(scanPackage+"."+file.getName().replace(".class","").trim());
            }
        }
    }

    private void doLoadConfig(String location) {
        try(InputStream ins=this.getClass().getClassLoader().getResourceAsStream(location)) {
            p.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"+ Arrays.toString(e.getStackTrace())
            .replaceAll("\\[|\\]","").replaceAll(",\\s","\r\n"));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(this.handlerMapping.isEmpty())return;
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 not found!");
            return ;
        }
        Method method = this.handlerMapping.get(url);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameterValues=new Object[parameterTypes.length];
        Map<String,String[]> parameterMap = req.getParameterMap();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType==HttpServletRequest.class){
                parameterValues[i]=req;
                continue;
            }else if(parameterType==HttpServletResponse.class){
                parameterValues[i]=resp;
                continue;
            }else if(parameterType==String.class){
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue())
                            .replaceAll("\\[|\\]", "")
                            .replaceAll(",\\s", ",");
                    parameterValues[i]= value;
                }
            }
        }
        try {
            String beanName = lowerFirstCase(method.getDeclaringClass().getSimpleName());
            method.invoke(this.ioc.get(beanName),parameterValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
