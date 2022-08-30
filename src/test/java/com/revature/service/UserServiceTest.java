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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.revature.dtos.Credentials;
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

    /* ___________________ GET USER BY EMAIL TESTS ________________ */

    @Test
    void testGetUserByEmail_Succeess() {
        String email = this.dummyUser.getEmail();

        given(this.mockUserRepo.findByEmail(email)).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userServ.getUserByEmail(email);

        assertEquals(expected, actual);
    }

    @Test
    void testGetUserByEmail_UserNotFoundException() {
        String email = "dummy1@dumbdumb.com";

        given(this.mockUserRepo.findByEmail(email)).willReturn(Optional.empty());

        try {
            this.userServ.getUserByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals(UserNotFoundException.class, e.getClass());
        }
    }

    /* ___________________ GET USER BY CREDENTIALS TESTS ________________ */

    @Test
    void testGetUserByCredentials_Success() {
        String email = this.dummyUser.getEmail();
        String pswd = this.dummyUser.getPswd();

        Credentials creds = new Credentials(email, pswd);

        given(this.mockUserRepo.findByEmail(creds.getEmail())).willReturn(Optional.of(this.dummyUser));
        given(this.mockUserRepo.findByEmailAndPswd(creds.getEmail(), creds.getPswd()))
                .willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userServ.getUserByCredentials(creds);

        assertEquals(expected, actual);

        verify(this.mockUserRepo, times(1)).findByEmailAndPswd(email, pswd);
    }

    @Test
    void testGetUserByCredentials_FailureThroughInvalidEmail() {
        String email = "dummy1111@dumbdumb.com";
        String pswd = this.dummyUser.getPswd();

        Credentials creds = new Credentials(email, pswd);

        given(this.mockUserRepo.findByEmail(creds.getEmail())).willReturn(Optional.ofNullable(null));

        User expected = null;
        User actual = this.userServ.getUserByCredentials(creds);

        assertEquals(expected, actual);

        verify(this.mockUserRepo, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByCredentials_FailureThroughInvalidPassword() {
        String email = this.dummyUser.getEmail();
        String pswd = "Dummy111";

        Credentials creds = new Credentials(email, pswd);

        given(this.mockUserRepo.findByEmail(creds.getEmail())).willReturn(Optional.of(this.dummyUser));
        given(this.mockUserRepo.findByEmailAndPswd(creds.getEmail(), creds.getPswd()))
                .willReturn(Optional.ofNullable(null));

        User expected = null;
        User actual = this.userServ.getUserByCredentials(creds);

        assertEquals(expected, actual);

        verify(this.mockUserRepo, times(1)).findByEmailAndPswd(email, pswd);
    }

    /* ___________________ ADD USER TEST___________________ */

    @Test
    void testAddUser_Success() {

        given(this.mockUserRepo.save(this.dummyUser)).willReturn(this.dummyUser);

        User expected = this.dummyUser;
        User actual = this.userServ.addUser(this.dummyUser);

        assertEquals(expected, actual);
    }

    /* ___________________ UPDATE USER STATS TEST___________________ */

    @Test
    void testUpdateStats() {

        int id = this.dummyUser.getId();
        double netProfit = 2000;

        given(this.mockUserRepo.findById(id)).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        System.out.println(this.dummyUser.toString() + "\n");
        User actual = this.userServ.updateStats(id, netProfit);
        System.out.println("\n" + this.dummyUser.toString() + "\n");

        assertEquals(expected, actual);

    }
}
