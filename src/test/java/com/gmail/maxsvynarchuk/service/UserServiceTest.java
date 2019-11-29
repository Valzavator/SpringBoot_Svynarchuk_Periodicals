package com.gmail.maxsvynarchuk.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
//    @InjectMocks
//    private UserService userService = UserService.getInstance();
//    @Mock
//    private UserDao userDao;
//    private String email = "email@gmail.com";
//    private String password = "password";
//    private String hashPassword = PasswordManager.hashPassword(password);
//
//    @Test
//    void findUserByIdWithExistingUserTest() {
//        Long userId = 1L;
//        Optional<User> expected = Optional.of(
//                User.newBuilder()
//                        .setId(userId)
//                        .build());
//        when(userDao.findOne(userId)).thenReturn(expected);
//
//        Optional<User> actual = userService.findUserById(userId);
//
//        assertEquals(expected, actual);
//        verify(userDao, times(1)).findOne(userId);
//    }
//
//    @Test
//    void findUserByIdWithNotExistingUserTest() {
//        Long userId = 1L;
//        when(userDao.findOne(userId)).thenReturn(Optional.empty());
//
//        Optional<User> periodicalOpt = userService.findUserById(userId);
//
//        assertFalse(periodicalOpt.isPresent());
//        verify(userDao, times(1)).findOne(userId);
//    }
//
//    @Test
//    void signInWithCorrectEmailAndPasswordTest() {
//        Optional<User> expected = Optional.of(User.newBuilder()
//                .setEmail(email)
//                .setPassword(hashPassword)
//                .build());
//        when(userDao.findOneByEmail(email)).thenReturn(expected);
//
//        Optional<User> actual = userService.signIn(email, password);
//
//        assertEquals(expected, actual);
//        verify(userDao, times(1)).findOneByEmail(email);
//    }
//
//    @Test
//    void signInWithWrongPasswordTest() {
//        Optional<User> findByEmailUser = Optional.of(User.newBuilder()
//                .setEmail(email)
//                .setPassword(hashPassword)
//                .build());
//        when(userDao.findOneByEmail(email)).thenReturn(findByEmailUser);
//
//        Optional<User> actual = userService.signIn(email, "another password");
//
//        assertFalse(actual.isPresent());
//        verify(userDao, times(1)).findOneByEmail(email);
//    }
//
//    @Test
//    void signInWithWrongEmailTest() {
//        when(userDao.findOneByEmail(email)).thenReturn(Optional.empty());
//
//        Optional<User> actual = userService.signIn(email, password);
//
//        assertFalse(actual.isPresent());
//        verify(userDao, times(1)).findOneByEmail(email);
//    }
//
//    @Test
//    void signInWithNullEmailTest() {
//        Optional<User> actual = userService.signIn(null, password);
//
//        assertFalse(actual.isPresent());
//        verify(userDao, never()).findOneByEmail(email);
//    }
//
//    @Test
//    void signInWithNullPasswordTest() {
//        Optional<User> actual = userService.signIn(email, null);
//
//        assertFalse(actual.isPresent());
//        verify(userDao, never()).findOneByEmail(email);
//    }
//
//    @Test
//    void registerUserWithValidParametersTest() {
//        User user = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .build();
//        when(userDao.existByEmail(email)).thenReturn(false);
//
//        assertTrue(userService.registerUser(user));
//        assertTrue(RoleType.USER.isEquals(user));
//        assertEquals(hashPassword, user.getPassword());
//        verify(userDao, times(1)).insert(any(User.class));
//    }
//
//    @Test
//    void registerUserWithSameEmailTest() {
//        User user = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .build();
//        when(userDao.existByEmail(email)).thenReturn(true);
//
//        assertFalse(userService.registerUser(user));
//        verify(userDao, never()).insert(any(User.class));
//    }
//
//    @Test
//    void registerUserWithNullEmailTest() {
//        User user = User.newBuilder()
//                .setEmail(null)
//                .setPassword(password)
//                .build();
//
//        assertFalse(userService.registerUser(user));
//        verify(userDao, never()).insert(any(User.class));
//    }
//
//    @Test
//    void registerUserWithNullPasswordTest() {
//        User user = User.newBuilder()
//                .setEmail(email)
//                .setPassword(null)
//                .build();
//
//        assertFalse(userService.registerUser(user));
//        verify(userDao, never()).insert(any(User.class));
//    }
}