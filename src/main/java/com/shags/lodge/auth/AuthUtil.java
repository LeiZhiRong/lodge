package com.shags.lodge.auth;

import com.shags.lodge.util.CmsUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class AuthUtil {
    /**
     * 初始化系统的角色所访问的功能信息
     */
    public static Map<String, Set<String>> initAuth(String pName) {
        try {
            Map<String, Set<String>> auths = new HashMap<String, Set<String>>();
            List<String> list = CmsUtils.string2Array(pName, ",");
            if (list.size() > 0) {
                for (String name : list) {
                    List<String> ps = getClassByPackage(name);
                    if (ps != null) {
                        for (String p : ps) {
                            String pc = name + "." + p.substring(0, p.lastIndexOf(".class"));
                            if (!(Class.forName(pc).isAnnotationPresent(AuthClass.class)))
                                continue;
                            Method[] ms = (Class.forName(pc)).getDeclaredMethods();
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
     * @param pName 包名称
     */
    private static List<String> getClassByPackage(String pName) {
        try {
            String pr = pName.replace(".", "/");
            String pp = Objects.requireNonNull(AuthUtil.class.getClassLoader().getResource(pr)).getPath();
            File file = new File(pp);
            return Arrays.asList(Objects.requireNonNull(file.list((dir, name) -> name.endsWith(".class"))));
        } catch (Exception e) {
            return null;
        }
    }
}

