package Lab10;

public class Dog implements CanSwim, CanWalk, Comparable<Dog>{
    private int age;
    private String breed;
    private String color;

    public Dog(int age, String breed, String color) {
        this.age = age;
        this.breed = breed;
        this.color = color;
    }

    @Override
    public void swim() {
        System.out.println("chlap chlap");
    }

    @Override
    public void walk() {
        System.out.println("ale sobie biegam heh");
    }


    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", breed='" + breed + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public int compareTo(Dog o) {
        //return  this.age - o.age;
        return  this.breed.compareTo(o.breed);
        //return  this.breed.compareTo(o.color);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
