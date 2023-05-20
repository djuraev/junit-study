package io.persona.unit.service;


import io.persona.User;
import io.persona.UserDao;
import io.persona.UserService;
import io.persona.unit.TestBase;
import io.persona.unit.extension.PostProcessingExtension;
import io.persona.unit.extension.UserServiceParamResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;


import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fast")
@Tag("user")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith({
        UserServiceParamResolver.class,
        PostProcessingExtension.class
})
public class UserServiceTest extends TestBase {
    //
    private static final User IVAN = new User(1, "Ivan", "123");
    private static final User PETR = new User(2, "Petr", "321");
    private UserService userService;
    private UserDao userDao;

    public UserServiceTest(TestInfo testInfo) {
        //
        System.out.println(testInfo);
    }

    @BeforeAll
    void startTesting() {
        //
        System.out.println("We are starting testing(1, 2, 3...)");
    }

    @BeforeEach
    void prepare() {
        //
        System.out.println("Before each: " + this);
        this.userDao = Mockito.spy(new UserDao());
        this.userService = new UserService(userDao);
    }

    @Test
    void shouldDeleteExistedUser() {
        userService.add(IVAN);
        Mockito.doReturn(true).when(userDao).delete(IVAN.getId());
        /*Mockito.when(userDao.delete(IVAN.getId()))
                .thenReturn(true)
                .thenReturn(false);*/

        var deleted = userService.delete(IVAN.getId());
        System.out.println(userService.delete(IVAN.getId()));
        System.out.println(userService.delete(IVAN.getId()));

        Mockito.verify(userDao, Mockito.times(2)).delete(IVAN.getId());
        
        assertTrue(deleted);
    }

    @Test
    @Order(1)
    @DisplayName("Users will be empty if no user added")
    void userEmptyIfNoUserAdded() {
        //
        System.out.println("Test 1: " + this);
        var users = userService.getAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void usersSizeIfUserAdded() {
        //
        System.out.println("Test 2: " + this);

        userService.add(new User());
        userService.add(new User());

        assertEquals(2, userService.getAll().size());
    }

    @AfterEach
    void cleanUp() {
        //
        System.out.println("After each: " + this);
    }


    @Nested
    @Tag("login")
    @DisplayName("Test User login functionality")
    class LoginTest {
        //

        @Test
        void loginFailIfPasswordIsNotCorrect() {
            //
            userService.add(IVAN);
            var maybeUser = userService.login(IVAN.getUsername(), "dummy");

            assertTrue(maybeUser.isEmpty());
        }

        //@Test
        @RepeatedTest(value = 5, name = RepeatedTest.DISPLAY_NAME_PLACEHOLDER)
        void loginFailIfUserDoesNotExist(RepetitionInfo repetitionInfo) {
            //
            System.out.println(repetitionInfo);
            userService.add(IVAN);
            var maybeUser = userService.login("dummy", IVAN.getPassword());
            assertTrue(maybeUser.isEmpty());
        }

        @Test
        void loginSuccessIfUserExists() {
            //
            userService.add(IVAN);
            Optional<User> user = userService.login(IVAN.getUsername(), IVAN.getPassword());

            assertTrue(user.isPresent());
            user.ifPresent(user1 -> assertTrue(user1.equals(IVAN)));
        }

        @Test
        void checkLoginFunctionalityPerformance() {
            assertTimeout(Duration.ofMillis(200L), () ->userService.login("dummy", "123"));
        }

        @Test
        void throwExceptionIfUsernameOrPasswordIsNull() {
            //
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "dummy")),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login("summy", null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, null))
            );
        }

        @ParameterizedTest
        //@NullSource
        //@EmptySource
        /*@ValueSource(strings = {
                "Ivan", "Petr"
        })*/
        //@EnumSource
        //@MethodSource("io.persona.unit.service.UserServiceTest#getArgumentsForLogin")
        @CsvFileSource(resources = "/login-test-data.csv", numLinesToSkip = 1)
        @CsvSource({
                "Ivan,123",
                "Petr,321"
        })
        void loginParameterizedTest(String username, String password) {
            //
            userService.add(IVAN, PETR);
            var found = userService.login(username, password);
            assertTrue(!found.equals(null));
        }
    }

    static Stream<Arguments> getArgumentsForLogin() {;
        return Stream.of(
                Arguments.of("Ivan", "123", Optional.of(IVAN)),
                Arguments.of("Petr", "321", Optional.of(PETR)),
                Arguments.of("Ivan", "dummy", Optional.empty()),
                Arguments.of("dummy", "123", Optional.empty())
        );
    }
}
