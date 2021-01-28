package com.lib.basex;

import com.lib.basex.activity.LStateViewModel;
import com.lib.basex.activity.LViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2021/1/28
 * 简 述：<功能简述>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("start");
        Abc lStateViewModel = new Abc();
        List<String> list = new ArrayList<>();
        System.out.println(isSub(list.getClass(), LViewModel.class));
    }


    public static boolean isSub(Class c, Class s) {
        Type type = c.getGenericSuperclass();
        if (type instanceof Class) {
            Class tem = ((Class) type);
            System.out.println(tem);
            if (tem.getName().equals(s.getName())) {
                return true;
            }
            if (tem.getName().equals(Object.class.getName())) {
                return false;
            }
            return isSub(tem, s);
        }
        return false;
    }
}
