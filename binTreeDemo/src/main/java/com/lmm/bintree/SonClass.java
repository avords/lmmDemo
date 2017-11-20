package com.lmm.bintree;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SonClass extends ParentClass{
    public int a=11;
    public int b=22;
    @Override
    public int getA() {
        return this.a;
    }

    @Override
    public int getB() {
        return this.b;
    }
    public int getC(){
        return super.getA();
    }

    public static void main(String[] args) {
        SonClass sonClass = new SonClass();
        System.out.println(sonClass.getC());
    }
}
