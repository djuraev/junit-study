package io.persona.unit.service;


import io.persona.User;
import io.persona.UserService;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fast")
@Tag("user")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
class UserServiceTest {
    //
    private static final User IVAN = new User(1, "Ivan", "123");
    private static final User PETR = new User(2, "Petr", "321");
    private UserService userService;

    @BeforeAll
    void startTesting() {
        //
        System.out.println("We are starting testing(1, 2, 3...)");
    }

    @BeforeEach
    void prepare() {
        //
        System.out.println("Before each: " + this);
        userService = new UserService();
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

        @Test
        void loginFailIfUserDoesNotExist() {
            //
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
        void throwExceptionIfUsernameOrPasswordIsNull() {
            //
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "dummy")),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login("summy", null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, null))
            );
        }
    }
}
