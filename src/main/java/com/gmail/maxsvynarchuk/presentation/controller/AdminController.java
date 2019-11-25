package com.gmail.maxsvynarchuk.presentation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@Log4j2
public class AdminController {

//    @GetMapping("/adminPage")
//    public String adminPage() {
//        log.info("Try to get admin page");
//        return Path.ADMIN_PAGE;
//    }
//
//    @GetMapping("/setGrade")
//    public String setGradePage() {
//        log.info("Try to get setGrade page");
//        return Path.SET_GRADE_PAGE;
//    }
//
//    @GetMapping("/sendNotification")
//    public String sendNotificationPage() {
//        log.info("Try to get sendNotification page");
//        return Path.SEND_NOTIFICATION_PAGE;
//    }
//
//    @PostMapping("/findUserSettingGrade")
//    public String findUserSettingGrade(@RequestParam("first_name") String firstName,
//                                       @RequestParam("second_name") String secondName,
//                                       ModelMap model) {
//        log.info("Try to find users for setting a grade");
//        List<User> users = service.findUsers(firstName, secondName);
//        if (users.isEmpty()) {
//            log.info("There aren't any available users");
//            model.addAttribute(AttributeNames.FIRST_NAME, firstName);
//            model.addAttribute(AttributeNames.SECOND_NAME, secondName);
//        } else {
//            log.info("Users were successfully found");
//            List<Subject> subjects = service.getAllSubjects();
//            model.addAttribute(AttributeNames.SUBJECTS, subjects);
//            model.addAttribute(AttributeNames.USERS, users);
//        }
//        return Path.SET_GRADE_PAGE;
//    }
//
//    @PostMapping("/setGrade")
//    public String settingGrade(@RequestParam("subject_id") Integer subjectId,
//                               @RequestParam("user_id") Long userId,
//                               @RequestParam("result") Integer result,
//                               ModelMap model) {
//        log.info("Start setting grade process");
//        service.setGrade(userId, subjectId, result);
//        model.addAttribute(AttributeNames.SUCCESS, "success");
//        return Path.SET_GRADE_PAGE;
//    }
//
//    @PostMapping("/findUserSendingNotification")
//    public String findUserSendingNotification(@RequestParam("first_name") String firstName,
//                                              @RequestParam("second_name") String secondName,
//                                              ModelMap model) {
//        log.info("Try to find users and for sending a notification");
//        List<User> users = service.findUsers(firstName, secondName);
//        if (users.isEmpty()) {
//            log.info("There aren't any available users");
//            model.addAttribute(AttributeNames.FIRST_NAME, firstName);
//            model.addAttribute(AttributeNames.SECOND_NAME, secondName);
//        } else {
//            log.info("Users were successfully found");
//            model.addAttribute(AttributeNames.USERS, users);
//        }
//        return Path.SEND_NOTIFICATION_PAGE;
//    }
//
//    @PostMapping("/setUserStatus")
//    public String sendingNotification(@RequestParam("user_id") Long userId,
//                                      @RequestParam("userStatusId") Integer userStatusId,
//                                      ModelMap model) {
//        log.info("Start sending notification process");
//        service.setUserStatus(userId, userStatusId);
//        model.addAttribute(AttributeNames.SUCCESS, "success");
//        return Path.SEND_NOTIFICATION_PAGE;
//    }
}
