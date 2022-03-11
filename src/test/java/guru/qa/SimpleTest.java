package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class SimpleTest {

    @BeforeEach
    void precondition() {
        open("https://www.onlinetrade.ru/member/register.html");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    // Faker faker = new Faker(new Locale("ru"));
    //String emailname = faker.internet().emailAddress();
    // String phone = faker.phoneNumber().phoneNumber();


    @ValueSource(strings = {"Name12@gmail.com", "Name11@gmail.com"})
    @ParameterizedTest(name = "Проверка работы теста с аннотацией ValueSource")
    void commonselenideSearchTest(String testData) {
        $("#contact").setValue("Имя");
        $("#email").setValue(testData);
        $("#account_myPasswordEdit_2ID").setValue("Parole123!");
        $("#cellphone").setValue("+79266351411");
        $(".button").click();
        $(".coloredMessage ").shouldHave(text("Вы были успешно зарегистрированы! "));
    }

    @CsvSource(value = {
            "Имя1,  Name13@gmail.com, Parole123!,+79266351481, Вы были успешно зарегистрированы! ",
            "Имя2,  Name13@gmail.com, Parole123!,+79266351481, Пользователь с таким адресом e-mail уже зарегистрирован."
    })

    @ParameterizedTest(name = "Проверка правильности заполнения формы регистрации")
    void inputTextTest(String name, String emailname, String password, String phone, String expectedWithText) {
        $("#contact").setValue(name);
        $("#email").setValue(emailname);
        $("#account_myPasswordEdit_2ID").setValue(password);
        $("#cellphone").setValue(phone);
        $(".button").click();
        $(".coloredMessage ").shouldHave(text(expectedWithText));
    }


    static Stream<Arguments> mixedArgumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("Имя3", "Name14@gmail.com", "Parole123!", "+79264952481", "Вы были успешно зарегистрированы!"),
                Arguments.of("Имя4", "Name14@gmail.com", "Parole123!", "+79264924481", "Пользователь с таким адресом e-mail уже зарегистрирован.")
        );
    }

    @MethodSource(value = "mixedArgumentsTestDataProvider")
    @ParameterizedTest()
    void mixedArgumentsTest(String nameArg, String emailnameArg, String passwordArg, String phoneArg, String expectedWithTextArg) {
        $("#contact").setValue(nameArg);
        $("#email").setValue(emailnameArg);
        $("#account_myPasswordEdit_2ID").setValue(passwordArg);
        $("#cellphone").setValue(phoneArg);
        $(".button").click();
        $(".coloredMessage ").shouldHave(text(expectedWithTextArg));
    }

}

