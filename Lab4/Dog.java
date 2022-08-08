package Lab4;

public class Dog {
    private String breed;
    private int age;

    private Dog() {
        System.out.println("Hau hau");
        this.age = 1;
    }

    public Dog(String breed, int age) {
        this();
        this.breed = breed;
        this.age = age;
    }

    public void starzejSie() {
        age += 1;
    }

    public void hauHau() {
        System.out.println("Hau hau mam " + age + " lat");
    }

    public int getAge() { //getter
        return this.age;
    }

    void setAge(int age) { // setter
        if (age < 1) {
            this.age = 1;
        } else if (age > 18) {
            this.age = 18;
        } else {
            this.age = age;
        }
    }
}