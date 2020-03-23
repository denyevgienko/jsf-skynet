package optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalTest {

    User user = new User(56);

    @Test
    public void printNullAndNonNullValues() {
        String noNull = "Yes";
        String isNull = null;

        System.out.println("Non-Empty Optional: " + Optional.ofNullable(noNull));
        System.out.println("Empty Optional: " + Optional.ofNullable(isNull));
        //Output
        //Non-Empty Optional: Optional[Yes]
        //Empty Optional: Optional.empty
        Optional<String> optional = Optional.of("Jsfiller");
        assertTrue(optional.isPresent()); //ok
        optional = Optional.ofNullable(isNull); //если null то false
        assertFalse(optional.isPresent()); //ok
    }

    @Test
    public void getOptional(){
        Optional<String> opt = Optional.of("JACK");
        String name = opt.get();
        //assertEquals("JACK", opt);  //exception
        assertEquals("JACK", name); //ok
        opt = Optional.ofNullable(null);
        opt.ifPresent(x -> System.out.println(x)); //nothing
        System.out.println(opt.get()); //java.util.NoSuchElementException: No value present
    }

    @Test
    public void orElseGet(){
        String maybeNull = null;
        String value = Optional.ofNullable(maybeNull).orElse("JACK");
        assertEquals("JACK", value); //true
        maybeNull = null; // обнуляем
        value = Optional.ofNullable(maybeNull).orElseGet(() -> "JACK");
        assertEquals("JACK", value); //true
    }

    @Test
    public void mapMethod() throws Exception {
        Optional<OptionalTest.User> userTwo = Optional.of(new User(null, 56));
        String myName = userTwo.map(name -> user.getName()).orElse("undefined");
        System.out.println(myName); // output: undefined
        userTwo.map(name -> user.getName()).orElseThrow(() -> new Exception("User name not found"));  //java.lang.Exception: User name not found

    }

    @Test
    public void flatMapMethod() {
        assertEquals(Optional.of("TEST"), Optional.of("test").map(s -> s = "TEST"));  // Приводит к обьекту True
        assertEquals(Optional.of("TEST"), Optional.of("test").flatMap(s -> Optional.of("TEST")));  //Приводит к optional True
    }

    @Test
    public void filterMethod() {
        List<User> users = Arrays.asList(null, new User(15), new User("Vasya", 19), new User("Kirosin", 55), new User("Jack", 16));
        users.forEach(x -> {
            //Если обьект не null (Optional.ofNullable) ->  фильтруем по возрасту через filter()
            Optional.ofNullable(x).filter(a -> a.getAge() > 18).ifPresent(y -> {
                //если есть подходящий ifPresent, записываем в Optional имя
                Optional<String> adultUserName = Optional.ofNullable(y.getName());
                //Если имя не null выводим на экран
                adultUserName.ifPresent(s -> System.out.println(s));
            });
        });

        // Vasya
        //Kirosin
    }


    public class User {
        private String name;
        private int age;

        public User(int age) {
            this.age = age;
        }

        public User(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
