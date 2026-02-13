package org.selion_framework.lib;

public class Test {
    public static class Base {

    }

    public static class Extended1 extends Base {

    }

    public static class Extended1Extended1 extends Extended1 {

    }

    public static class Extended1Extended2 extends Extended1 {

    }

    public static class Extended2 extends Base {

    }

    public static class Extended2Extended1 extends Extended2 {

    }

    public static class Extended2Extended2 extends Extended2 {

    }

    public void set(Base... parameters) {}

    public void set2(Extended1... parameters) {}

    public static void main(String[] args) {
        Test test = new Test();

        test.set(new Extended1(), new Extended2(), new Extended1Extended1());
        test.set2(new Extended1(), new Extended2(), new Base());
    }
}
