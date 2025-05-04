package ru.netology.bank.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));
    private DataHelper() {

    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }
    @Value
    public static class User {
        private String status;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }
    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getFakeAuthInfo() {
        return new AuthInfo( faker.name().username(), faker.internet().password());
    }

    public static VerificationCode getFakeVerificationCode() {
        return new VerificationCode(faker.code().isbn10());
    }
}