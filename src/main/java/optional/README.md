# Optional<T> *JDK 8*


 Давным давно, класс Optional<T> был добавлен в *JDK 8*.
 Основная задача класса обработка null значений, например:
 
           String noNull = "Yes";
           String isNull = null;
   
           System.out.println("Non-Empty Optional: " + Optional.ofNullable(noNull));
           System.out.println(" Empty Optional: " + Optional.ofNullable(isNull));
           //Output
           Non-Empty Optional: Optional[Yes]
           Empty Optional: Optional.empty
 
  Класс Optional нельзя создать прямым образом через new. Он имеет приватный конструктор. 
  Но его можно создать при помощи статических методов, например: 
  
    Optional<String> empty = Optional.empty(); //  пустой объект Optional
    Optional<String> opt = Optional.of(name); // создание через статический метод of
    String str = element.getText();
    Optional<String> opt = Optional.ofNullable(str); // создание через статический метод ofNullable -> если ожидаем что значение может быть пустым
   
  Optional может содержать значение или некоторый тип Т или просто быть null. 
  Работу с методами можно посмотреть в тестовом классе OptionalTest, а основные фишки опишу ниже: 

  Итак, раньше мы проверяли на null так (если проверяли конечно): 
  
        String someString;
         if(someString != null) {
              System.out.println(someString.length);
        }

  Теперь божественные лямбды. 
  Проверка значения с помощью isPresent():
  
        Optional<String> optional = Optional.of("Jsfiller");
        assertTrue(optional.isPresent())
    
 Условное действие с ifPresent ()
 
        //Is present выполнит действие если обьект не пустой
        Optional<String> optional = Optional.of("Jsfiller");
        optional.ifPresent(x -> System.out.println(x));  
        
  Значение по умолчанию с orElse
  API orElse используется для получения значения, заключенного в экземпляр Optional . 
  Он принимает один параметр, который действует как значение по умолчанию. 
  С orElse возвращаемое значение возвращается, если оно присутствует, и аргумент, переданный orElse , 
  возвращается, если значение без переноса отсутствует:
  
          String maybeNull = selenidElem.getText();
          String value = Optional.ofNullable(nullmaybeNullName).orElse("JACK");
          assertEquals("JACK", value);
          
  orElseGet
  API orElseGet похож на orElse . Однако вместо того, чтобы принимать значение, возвращаемое, 
  если значение Optional отсутствует, он принимает функциональный интерфейс поставщика, который вызывается, и возвращает значение вызова:
  
      String maybeNull = selenidElem.getText();
      String value = Optional.ofNullable(maybeNull).orElseGet(() -> "JACK");
      assertEquals("JACK", value);
            
  Исключения с orElseThrow
  OrElseThrow API следует из orElse и orElseGet in и добавляет новый подход для обработки отсутствующего значения. 
  Вместо того, чтобы возвращать значение по умолчанию, когда упакованное значение отсутствует, оно генерирует исключение:
  
      String maybeNull = selenidElem.getText();
      String name = Optional.ofNullable(maybeNull).orElseThrow(() -> new Exception("Element not found"));
        
  
  Возвращаемое значение с помощью get ()
  Последний подход к получению упакованного значения - API get :
  
      Optional<String> opt = Optional.of("JACK");
      String name = opt.get();
      assertEquals("JACK", opt);  //exception
      assertEquals("JACK", name); //ok
  
  В отличие от вышеупомянутых трех подходов, API-интерфейс get может возвращать значение только в том случае, если обернутый объект не равен null, 
  в противном случае он выдает исключение :
  
      Optional<String> opt = Optional.ofNullable(null);
      String name = opt.get(); //java.util.NoSuchElementException: No value present

      
  Это недостаток get(). В идеале Optional должен помочь избежать исключений.
  Поэтому целесообразно использовать другие варианты, которые обработать null.
  
  *map()* -> похож на stream, но отработает только если optional != null (map должен вернуть обьект с которым ты работаешь)
  
       Optional<OptionalTest.User> user = Optional.of(new User(null, 56));
       String myName = user.map(name -> user.getName()).orElse("undefined"); //orElse присвоит значение если обьект не пустой
       //output -> myName = undefined
      
  *filter()* -> тот же стрим но с проверкой на null. 
  Тут пример как остортирвать очень мусорный массив на совершеннолетних юзеров где null может подобраться нежданно в любом 
  месте (обьект или поле обьекта), без if else проверок и try catch блоков.
  Чуть подробнее будет в тестовом классе
  
        List<User> users = Arrays.asList(null, new User(null, 15), new User("Vasya", 19), new User("Kirosin", 55), new User("Jack", 16));
        users.forEach(x -> {
            Optional.ofNullable(x).filter(a -> a.getAge() > 18).ifPresent(y -> {
                Optional<String> adultUserName = Optional.ofNullable(y.getName());
                adultUserName.ifPresent(s -> System.out.println(s));
            });
        });
        // Output Vasys and Kirosin  
        
  *flatMap()* -> возвращает не обьект с которым мы работаем а Optional, 
  пример:
  
       assertEquals(Optional.of("TEST"), Optional.of("test").map(s -> s = "TEST"));  // Приводит к обьекту True
       assertEquals(Optional.of("TEST"), Optional.of("test").flatMap(s -> Optional.of("TEST")));  //Приводит к optional True
       
       
  В JDK 9 класс Optional был обновлен и в него добавили новые методы типа or() и возможности 
  преобразования в stream(), но это уже совсем другая история ... 
  
        
  
        
  
        
 
        
  
        
 

  
  
                                
                                       

 
