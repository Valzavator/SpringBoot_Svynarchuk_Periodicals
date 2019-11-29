package com.gmail.maxsvynarchuk.authorization;

class PostSignInCommandTest {
//    @InjectMocks
//    private PostSignInCommand postSignInCommand;
//    @Mock
//    private UserService userService;
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//    @Mock
//    private HttpSession session;
//
//    private final String email = "email@gmail.com";
//    private final String password = "password";
//
//    @Test
//    void executeForAdminCredentialsTest() {
//        CommandResult expected = CommandResult.redirect(PagesPaths.ADMIN_CATALOG_PATH);
//        User admin = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .setRole(RoleType.ADMIN.getValue())
//                .build();
//        when(request.getSession()).thenReturn(session);
//        when(request.getParameter(RequestParameters.USER_EMAIL)).thenReturn(email);
//        when(request.getParameter(RequestParameters.USER_PASSWORD)).thenReturn(password);
//        when(userService.signIn(email, password)).thenReturn(Optional.of(admin));
//
//        CommandResult actual = postSignInCommand.execute(request, response);
//
//        assertEquals(expected, actual);
//        verify(userService, times(1)).signIn(email, password);
//        verify(session, times(1)).setAttribute(Attributes.USER, admin);
//    }
//
//    @Test
//    void executeForUserCredentialsTest() {
//        CommandResult expected = CommandResult.redirect(PagesPaths.CATALOG_PATH);
//        User admin = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .setRole(RoleType.USER.getValue())
//                .build();
//        when(request.getSession()).thenReturn(session);
//        when(request.getParameter(RequestParameters.USER_EMAIL)).thenReturn(email);
//        when(request.getParameter(RequestParameters.USER_PASSWORD)).thenReturn(password);
//        when(userService.signIn(email, password)).thenReturn(Optional.of(admin));
//
//        CommandResult actual = postSignInCommand.execute(request, response);
//
//        assertEquals(expected, actual);
//        verify(userService, times(1)).signIn(email, password);
//        verify(session, times(1)).setAttribute(Attributes.USER, admin);
//    }
//
//    @Test
//    void executeForInvalidCredentialsTest() {
//        CommandResult expected = CommandResult.forward(Views.SIGN_IN_VIEW);
//        User invalidUser = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .build();
//        Map<String, Boolean> errors = new HashMap<>();
//        errors.put(Attributes.ERROR_AUTHENTICATION, true);
//        when(request.getParameter(RequestParameters.USER_EMAIL)).thenReturn(email);
//        when(request.getParameter(RequestParameters.USER_PASSWORD)).thenReturn(password);
//        when(userService.signIn(email, password)).thenReturn(Optional.empty());
//
//        CommandResult actual = postSignInCommand.execute(request, response);
//
//        assertEquals(expected, actual);
//        verify(userService, times(1)).signIn(email, password);
//        verify(session, never()).setAttribute(anyString(), any(User.class));
//        verify(request, times(1)).setAttribute(Attributes.ERRORS, errors);
//        verify(request, times(1)).setAttribute(Attributes.USER, invalidUser);
//    }
//
//    @Test
//    void executeForInvalidEmailParameterTest() {
//        CommandResult expected = CommandResult.forward(Views.SIGN_IN_VIEW);
//        User invalidUser = User.newBuilder()
//                .setEmail(null)
//                .setPassword(password)
//                .build();
//        Map<String, Boolean> errors = new HashMap<>();
//        errors.put(Attributes.ERROR_EMAIL, true);
//        when(request.getParameter(RequestParameters.USER_EMAIL)).thenReturn(null);
//        when(request.getParameter(RequestParameters.USER_PASSWORD)).thenReturn(password);
//
//        CommandResult actual = postSignInCommand.execute(request, response);
//
//        assertEquals(expected, actual);
//        verify(userService, never()).signIn(email, password);
//        verify(session, never()).setAttribute(anyString(), any(User.class));
//        verify(request, times(1)).setAttribute(Attributes.ERRORS, errors);
//        verify(request, times(1)).setAttribute(Attributes.USER, invalidUser);
//    }
//
//    @Test
//    void executeForInvalidPasswordParameterTest() {
//        CommandResult expected = CommandResult.forward(Views.SIGN_IN_VIEW);
//        User invalidUser = User.newBuilder()
//                .setEmail(email)
//                .setPassword(null)
//                .build();
//        Map<String, Boolean> errors = new HashMap<>();
//        errors.put(Attributes.ERROR_PASSWORD, true);
//        when(request.getParameter(RequestParameters.USER_EMAIL)).thenReturn(email);
//        when(request.getParameter(RequestParameters.USER_PASSWORD)).thenReturn(null);
//
//        CommandResult actual = postSignInCommand.execute(request, response);
//
//        assertEquals(expected, actual);
//        verify(userService, never()).signIn(email, password);
//        verify(session, never()).setAttribute(anyString(), any(User.class));
//        verify(request, times(1)).setAttribute(Attributes.ERRORS, errors);
//        verify(request, times(1)).setAttribute(Attributes.USER, invalidUser);
//    }
}