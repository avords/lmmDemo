package com.lmm.test.logDemo;

import java.util.Scanner;

/**
 * Created by Administrator on 2016/11/18.
 */
public class ZhengZeBiaoDaShiDemo {
    public static void main(String[] args) {
        while (true){
            System.out.print("请输入验证字符:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            boolean flag = str.matches("^.*([\u4e00-\u9fa5]|\\s|\\d|[A-Z])+.*$");
            System.out.println(flag);
        }
    }
}
