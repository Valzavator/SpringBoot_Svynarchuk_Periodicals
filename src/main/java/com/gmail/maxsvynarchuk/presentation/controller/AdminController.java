package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.dao.*;
import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/app/")
@SessionAttributes("lang")
@AllArgsConstructor
@Log4j2
public class AdminController {
//    private final AdminService service;

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PaymentDao paymentDao;
    private final PeriodicalDao periodicalDao;
    private final SubscriptionDao subscriptionDao;

//    @GetMapping("/")
//    public String adminPage() {
////        log.error(userDao.findAll().toString());
////        log.error(roleDao.findAll().toString());
////        Payment p = Payment.builder()
////                .paymentDate(LocalDateTime.now())
////                .totalPrice(new BigDecimal("111111111.11"))
////                .user(User.builder().userId(1L).build())
////                .build();
////        log.error(periodicalDao.findAll().toString());
//
////        log.error(paymentDao.findAll(PageRequest.of(0, 10)).getContent().toString());
////        log.error(subscriptionDao.isUserAlreadySubscribed(
////                User.builder().userId(2L).build(),
////                Periodical.builder().periodicalId(12L).build()));
////        log.error(subscriptionDao.findByUserAndStatus(
////                User.builder().userId(2L).build(),
////                false,
////                PageRequest.of(0, 10)).getContent().toString());
//        log.error("\n\n{}\n\n", Views.HOME_VIEW);
//        return Views.HOME_VIEW;
//    }

    @GetMapping("/signin")
    public String signInPage() {

        log.error("\n\n{}\n\n", Views.SIGN_IN_VIEW);
        return Views.SIGN_IN_VIEW;
    }



}
