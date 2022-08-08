package Lab10;

import java.util.Arrays;
import java.util.Collections;

public class main {
    public static void main(String[] args) {
        //CanSwim[] pływacze = {new DOg(), new Motorboat()};

       // for(CanSwim pływak : pływacze){
        //    pływak.swim();
       // }


        Dog dog1 = new Dog(3,"Labrador","beige");
        Dog dog2 = new Dog(2,"Jamnik","rozwy");
        Dog dog3 = new Dog(5,"Kapucynka","white");

        Dog[] dogs= {dog1, dog2, dog3};

        Arrays.sort(dogs, new DogAgeComparator());

        System.out.println(Arrays.toString(dogs));
    }
}


