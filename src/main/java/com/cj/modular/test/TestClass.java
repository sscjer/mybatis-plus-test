package com.cj.modular.test;


import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author Caoj
 * @date 2022-05-21 10:47
 */
public class TestClass {
    public static void main(String[] args) throws Exception {
        TreeSet<Book> treeSet = new TreeSet<>();
        treeSet.add(new Book("红楼梦", "曹雪芹", 50.0));
        treeSet.add(new Book("三国演义", "罗贯中", 40.0));
        treeSet.add(new Book("西游记", "吴承恩", 38.0));
        treeSet.add(new Book("水浒传", "施耐庵", 35.0));
        treeSet.add(new Book("水浒传", "施耐庵1", 35.0));
        treeSet.add(new Book("水浒传", "施耐庵3", 35.0));
        treeSet.forEach(System.err::println);
        new ArrayList<>();
    }
}