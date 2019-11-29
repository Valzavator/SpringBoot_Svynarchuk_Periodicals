package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO;
import com.gmail.maxsvynarchuk.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
@Slf4j
public class UserController {
    SubscriptionService subscriptionService;

    @GetMapping("/profile")
    public String getProfile() {
        return Views.PROFILE_VIEW;
    }

    @GetMapping("/subscriptions")
    public String getSubscriptionsPage(
            @RequestParam(required = false, defaultValue = "0") int activePage,
            @RequestParam(required = false, defaultValue = "0") int expiredPage,
            @RequestParam(required = false) String pill,
            @SessionAttribute("user") User user,
            Model model,
            RedirectAttributes redirectAttributes) {
        log.debug("Attempt to get subscriptions page");
        if (Objects.nonNull(pill)) {
            model.addAttribute(pill, true);
        }
        Page<Subscription> activeSubscriptions =
                subscriptionService.findAllSubscriptionsByUserAndStatus(user,
                        false,
                        activePage,
                        Pagination.FIVE_RECORDS_PER_PAGE);
        Page<Subscription> expiredSubscriptions =
                subscriptionService.findAllSubscriptionsByUserAndStatus(user,
                        true,
                        expiredPage,
                        Pagination.FIVE_RECORDS_PER_PAGE);
        if (activeSubscriptions.getTotalPages() > 0) {
            if (!activeSubscriptions.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PILL,
                        pill);
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE,
                        activeSubscriptions.getTotalPages() - 1);
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE,
                        expiredPage);
                return ControllerUtil.redirectTo(PagesPaths.SUBSCRIPTIONS_PATH);
            }
            PageDTO<Subscription> activePageDTO = new PageDTO<>(activeSubscriptions.getContent(),
                    activeSubscriptions.getNumber(),
                    activeSubscriptions.getTotalPages());
            model.addAttribute(Attributes.ACTIVE_SUBSCRIPTIONS, activePageDTO);
        }
        if (expiredSubscriptions.getTotalPages() > 0) {
            if (!expiredSubscriptions.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PILL,
                        pill);
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE,
                        activePage);
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE,
                        expiredSubscriptions.getTotalPages() - 1);
                return ControllerUtil.redirectTo(PagesPaths.SUBSCRIPTIONS_PATH);
            }
            PageDTO<Subscription> expiredPageDTO = new PageDTO<>(expiredSubscriptions.getContent(),
                    expiredSubscriptions.getNumber(),
                    expiredSubscriptions.getTotalPages());
            model.addAttribute(Attributes.EXPIRED_SUBSCRIPTIONS, expiredPageDTO);
        }
        return Views.SUBSCRIPTIONS_VIEW;
    }
}
