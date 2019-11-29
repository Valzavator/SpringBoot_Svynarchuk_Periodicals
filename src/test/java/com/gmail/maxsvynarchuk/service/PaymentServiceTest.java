package com.gmail.maxsvynarchuk.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceTest {
//    @InjectMocks
//    private PaymentService paymentService = PaymentService.getInstance();
//    @Mock
//    private PaymentDao paymentDao;
//
//    @Test
//    void findPaymentByIdWithExistingPaymentTest() {
//        Long paymentId = 1L;
//        Optional<Payment> expected = Optional.of(
//                Payment.newBuilder()
//                .setId(paymentId)
//                .build());
//        when(paymentDao.findOne(paymentId)).thenReturn(expected);
//
//        Optional<Payment> actual = paymentService.findPaymentById(paymentId);
//
//        assertEquals(expected, actual);
//        verify(paymentDao, times(1)).findOne(paymentId);
//    }
//
//    @Test
//    void findPaymentByIdWithNotExistingPaymentTest() {
//        Long paymentId = 1L;
//        when(paymentDao.findOne(paymentId)).thenReturn(Optional.empty());
//
//        Optional<Payment> paymentOpt = paymentService.findPaymentById(paymentId);
//
//        assertFalse(paymentOpt.isPresent());
//        verify(paymentDao, times(1)).findOne(paymentId);
//    }
//
//    @Test
//    void findAllPaymentsTest() {
//        long skip = 0;
//        long limit = 5;
//
//        paymentService.findAllPayments(skip, limit);
//
//        verify(paymentDao, times(1)).findAll(skip, limit);
//    }
//
//    @Test
//    void createPayment() {
//        Long paymentID = 1L;
//        User user = User.newBuilder()
//                .setId(1L)
//                .build();
//        BigDecimal totalPrice = new BigDecimal("10");
//        when(paymentDao.insert(any(Payment.class))).then((Answer<Payment>) invocationOnMock -> {
//            Payment payment = invocationOnMock.getArgument(0);
//            payment.setId(paymentID);
//            return payment;
//        });
//
//        Payment actual = paymentService.createPayment(user, totalPrice);
//
//        assertEquals(paymentID, actual.getId());
//        assertEquals(user, actual.getUser());
//        assertEquals(totalPrice, actual.getTotalPrice());
//        assertNotNull(actual.getPaymentDateTime());
//        verify(paymentDao, times(1)).insert(actual);
//    }
//
//    @Test
//    void getPaymentsCountTest() {
//        long expected = 10;
//        when(paymentDao.getCount()).thenReturn(expected);
//
//        long actual = paymentService.getPaymentsCount();
//
//        assertEquals(expected, actual);
//        verify(paymentDao, times(1)).getCount();
//    }
}