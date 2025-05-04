package ru.netology.bank.test;

import org.junit.jupiter.api.*;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.data.SQLHelper;
import ru.netology.bank.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.bank.data.DataHelper.*;


public class LoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }


    @AfterAll
    static void cleanup() {
        SQLHelper.cleanDatabase();
    }


    @Test
    public void successLogin() throws SQLException {
        var authInfo = getAuthInfo(); // vasya/qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyPageVisible();
        var code = SQLHelper.getVerificationCode(authInfo.getLogin());
        var verificationCode = new DataHelper.VerificationCode(code);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void wrongCredentials() {
        var authInfo = getFakeAuthInfo();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    public void wrongCode() {
        var authInfo = getAuthInfo();
        var authorizationCodePage = loginPage.validLogin(authInfo);
        authorizationCodePage.verifyPageVisible();
        VerificationCode verificationCode = getFakeVerificationCode();
        authorizationCodePage.invalidVerify(verificationCode);
        authorizationCodePage.verifyErrorNotificationVisibility();
    }

    @Test
    public void wrongCredentials3Times() {
        var authInfo = getAuthInfoPasswordNoLogin();
        var authInfoFake = getFakeInfo();
        var authInfoFakePassword = getFakePasswordInfoNoLogin();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearForm();
        loginPage.login(authInfoFakePassword);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearForm();
        loginPage.login(authInfoFakePassword);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearForm();

        loginPage.login(authInfoFake);
        loginPage.verifyErrorNotificationVisibility();
    }
}
