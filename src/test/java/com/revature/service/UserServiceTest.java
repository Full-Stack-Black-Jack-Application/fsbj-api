package com.revature.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository mockUserRepo;

    @InjectMocks
    UserService userServ;

    User dummyUser;

    @BeforeEach
    void setUp() throws Exception {
        this.dummyUser = new User(1, 0, 0, 0.0, 0.0, "dummyfirst", "dummylast", "dummy@dumbdumb.com", "Dummy123",
                "DSL-23L-A9S");
    }

    @AfterEach
    void tearDown() throws Exception {
        // Set up dummyUser for Garbage Collection
        this.dummyUser = null;
    }

    /* ___________________ GET USER BY ID TESTS ________________ */

    @Test
    void testGetUserById_Success() {
        int id = 1;

        given(this.mockUserRepo.findById(id)).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userServ.getUserById(id);

        assertEquals(expected, actual);
    }

    @Test
    void testGetUserById_Failure_IDLessThanZero() {

        int id = -1;

        assertNull(this.userServ.getUserById(id));
    }

    @Test
    void testGetUserById_Failure_UserNotFoundException() {
        int id = 2;

        given(this.mockUserRepo.findById(id)).willReturn(Optional.empty());

        try {
            this.userServ.getUserById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals(UserNotFoundException.class, e.getClass());
        }

    }

}
