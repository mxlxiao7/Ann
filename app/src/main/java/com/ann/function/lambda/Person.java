package com.ann.function.lambda;

import java.util.Comparator;

/**
 * 测试用
 */
class Person {

    private String firstName;
    private String lastName;
    private int age;

    public Person(String fn, String ln, int a) {
        this.firstName = fn;
        this.lastName = ln;
        this.age = a;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    /**
     * 一般写法
     */
    public static final Comparator<Person> BY_FIRST = (lhs, rhs) -> lhs.firstName.compareTo(rhs.firstName);
    public static final Comparator<Person> BY_LAST = (lhs, rhs) -> lhs.lastName.compareTo(rhs.lastName);
    public static final Comparator<Person> BY_AGE = (lhs, rhs) -> lhs.age - rhs.age;


    /**
     * 这里匿名类实现可以用lambda表达式替换
     */
    public static final Comparator<Person> BY_LAST_AND_AGE = (lhs, rhs) -> {
        if (lhs.getLastName().equals(rhs.getLastName())) {
            return lhs.getAge() - rhs.getAge();
        } else {
            return lhs.getLastName().compareTo(rhs.getLastName());
        }
    };


    public static int compareLastAndAge(Person lhs, Person rhs) {
        if (lhs.lastName.equals(rhs.lastName)) {
            return lhs.age - rhs.age;
        } else {
            return lhs.lastName.compareTo(rhs.lastName);
        }
    }


    @Override
    public String toString() {
        return "Person{ " +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public static String toJSON(Person p) {
        return
                "{" +
                        "firstName: \"" + p.firstName + "\", " +
                        "lastName: \"" + p.lastName + "\", " +
                        "age: " + p.age + " " +
                        "}";
    }
}
