package io.persona.unit.service;


import io.persona.User;
import io.persona.UserService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    //
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

    @Test
    void throwExceptionIfUsernameOrPasswordIsNull() {
        //
        assertThrows(IllegalArgumentException.class, ()->userService.login(null, "dummy"));
    }

    @AfterEach
    void cleanUp() {
        //
        System.out.println("After each: " + this);
    }

}
