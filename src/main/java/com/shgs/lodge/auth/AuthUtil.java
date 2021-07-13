package com.shgs.lodge.auth;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class AuthUtil {
    /**
     * 初始化系统的角色所访问的功能信息
     *
     * @return
     */
    public static Map<String, Set<String>> initAuth(String pname) {
        try {
            Map<String, Set<String>> auths = new HashMap<>();
            String[] ps = getClassByPackage(pname);
            if (ps != null) {
                for (String p : ps) {
                    String pc = pname + "." + p.substring(0, p.lastIndexOf(".class"));
                    if (!(Class.forName(pc).isAnnotationPresent(AuthClass.class)))
                        continue;
                    // 获取每个类中的方法，以此确定哪些角色可以访问哪些方法
                    Method[] ms = (Class.forName(pc)).getDeclaredMethods();
                    /*
                     * 遍历method来判断每个method上面是否存在相应的AuthMethd
                     * 如果存在就直接将这个方法存储到auths中，如果不存在就不存储 不存储就意味着该方法只能由admin访问
                     */
                    for (Method m : ms) {
                        if (!m.isAnnotationPresent(AuthMethod.class))
                            continue;
                        // 如果存在就要获取这个Annotation
                        AuthMethod am = m.getAnnotation(AuthMethod.class);
                        String roles = am.role();
                        // 可能一个action可以被多个角色所访问，使用，进行分割
                        String[] aRoles = roles.split(",");
                        for (String role : aRoles) {
                            Set<String> actions = auths.computeIfAbsent(role, k -> new HashSet<>());
                            actions.add(pc + "." + m.getName());
                        }
                    }
                }
                return auths;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据包获取所有的类
     *
     * @param pname
     * @return
     */
    private static String[] getClassByPackage(String pname) {
        try {
            String pr = pname.replace(".", "/");
            String pp = Objects.requireNonNull(AuthUtil.class.getClassLoader().getResource(pr)).getPath();
            File file = new File(pp);
            return file.list((dir, name) -> name.endsWith(".class"));
        } catch (Exception e) {
            return null;
        }
    }
}

