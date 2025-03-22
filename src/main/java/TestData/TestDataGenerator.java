package TestData;

import com.github.javafaker.Faker;

public class TestDataGenerator {
    private final Faker faker;

    public TestDataGenerator(){
        this.faker = new Faker();
    }

    public String getRandomEmail(){
        return faker.internet().emailAddress();
    }

    public String getRandomPassword(){
        return faker.internet().password(6,8);
    }

    public String getRandomName(){
        return faker.name().username();
    }

    public String getRandomInvalidEmail(){
        return faker.internet().emailAddress("invalidMail1488");
    }

    public String getRandomInvalidPassword(){
        return faker.internet().password(2, 5);
    }
}
