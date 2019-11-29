package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.*;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.exception.NotFoundException;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO;
import com.gmail.maxsvynarchuk.presentation.util.dto.PeriodicalDTO;
import com.gmail.maxsvynarchuk.presentation.util.dto.PeriodicalIssueDTO;
import com.gmail.maxsvynarchuk.service.*;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/app/admin")
@AllArgsConstructor
@Log4j2
public class AdminController {
    private final PeriodicalService periodicalService;
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final IssueService issueService;

    private final ModelMapper mapper;

    @GetMapping("/catalog")
    public String getAdminCatalogPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.debug("Attempt to get admin catalog page");
        Page<Periodical> periodicals = periodicalService.findAllPeriodicals(page,
                Pagination.TEN_RECORDS_PER_PAGE);
        if (periodicals.getTotalPages() > 0) {
            if (!periodicals.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PAGE,
                        periodicals.getTotalPages() - 1);
                return ControllerUtil.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
            }
            PageDTO<Periodical> pageDTO = new PageDTO<>(periodicals.getContent(),
                    periodicals.getNumber(),
                    periodicals.getTotalPages());
            model.addAttribute(Attributes.PAGE_DTO, pageDTO);
        }
        return Views.ADMIN_CATALOG_VIEW;
    }

    @GetMapping("/catalog/periodical-create")
    public String getCreatePeriodicalPage(Model model) {
        log.debug("Attempt to get the page for creating a periodical");
        model.addAttribute(Attributes.PERIODICAL_TYPES,
                periodicalService.findAllPeriodicalTypes());
        model.addAttribute(Attributes.FREQUENCIES,
                periodicalService.findAllFrequencies());
        model.addAttribute(Attributes.PUBLISHERS,
                periodicalService.findAllPublishers());
        if (!model.containsAttribute(Attributes.PERIODICAL_DTO)) {
            model.addAttribute(Attributes.PERIODICAL_DTO, new PeriodicalDTO());
        }
        return Views.CREATE_PERIODICAL_VIEW;
    }

    @PostMapping("/catalog/periodical-create")
    public String createPeriodical(@Valid PeriodicalDTO periodicalDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        log.debug("Start of new periodical creation");

        if (bindingResult.hasErrors()) {
            log.debug("Invalid creation parameters");
            Map<String, Boolean> errors = ControllerUtil.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute(Attributes.ERRORS, errors);
            redirectAttributes.addFlashAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
            log.debug("Periodical creation fail");
            return ControllerUtil.redirectTo(PagesPaths.CREATE_PERIODICAL_PATH);
        }

        Periodical periodical = mapper.map(periodicalDTO, Periodical.class);
        try {
            periodicalService.createPeriodical(periodical);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
            throw new BadRequestException();
        }
        log.debug("Periodical was successfully create");
        return ControllerUtil.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
    }

    @GetMapping("/catalog/periodical-edit")
    public String getEditPeriodicalPage(@RequestParam Long periodicalId,
                                        Model model) {
        log.debug("Attempt to get page for editing periodical");
        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);

        if (periodicalOpt.isPresent()) {
            Periodical periodical = periodicalOpt.get();
            if (periodical.getStatus() == PeriodicalStatus.SUSPENDED) {
                log.debug("Can't edit periodical with suspended status");
                throw new BadRequestException();
            }
            PeriodicalDTO periodicalDTO = mapper.map(periodical, PeriodicalDTO.class);
            if (!model.containsAttribute(Attributes.PERIODICAL_DTO)) {
                model.addAttribute(Attributes.PERIODICAL_DTO,
                        periodicalDTO);
            }
            model.addAttribute(Attributes.PERIODICAL_TYPES,
                    periodicalService.findAllPeriodicalTypes());
            model.addAttribute(Attributes.FREQUENCIES,
                    periodicalService.findAllFrequencies());
            model.addAttribute(Attributes.PUBLISHERS,
                    periodicalService.findAllPublishers());

            log.debug("Attempt to get page for editing periodical is successful");
            return Views.EDIT_PERIODICAL_VIEW;
        }
        log.debug("Periodical with id {} doesn't exist", periodicalId);
        throw new NotFoundException();
    }

    @PostMapping("/catalog/periodical-edit")
    public String editPeriodical(
            @RequestHeader(value = "referer", required = false, defaultValue = "/app/admin/catalog") String referer,
            @Valid PeriodicalDTO periodicalDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        log.debug("Start editing of periodical");

        if (bindingResult.hasErrors()) {
            log.debug("Invalid editing parameters");
            Map<String, Boolean> errors = ControllerUtil.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute(Attributes.ERRORS, errors);
            redirectAttributes.addFlashAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
            redirectAttributes.addAttribute(RequestParameters.PERIODICAL_ID, periodicalDTO.getId());
            log.debug("Periodical editing fail");
            return ControllerUtil.redirectTo(referer);
        }

        Periodical periodical = mapper.map(periodicalDTO, Periodical.class);
        try {
            periodicalService.updatePeriodical(periodical);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
            throw new BadRequestException();
        }
        log.debug("Periodical was successfully edit");
        return ControllerUtil.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
    }

    @GetMapping("/catalog/issue-create")
    public String getCreateIssuePage(@RequestParam Long periodicalId,
                                     Model model) {
        log.debug("Attempt to get page for create issue");
        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);
        if (periodicalOpt.isPresent()) {
            Periodical periodicalDTO = periodicalOpt.get();
            if (periodicalDTO.getStatus() == PeriodicalStatus.SUSPENDED) {
                log.debug("Can't create issue for suspended periodical");
                throw new BadRequestException();
            }
            if (!model.containsAttribute(Attributes.ISSUE_DTO)) {
                model.addAttribute(Attributes.ISSUE_DTO, new PeriodicalIssueDTO());
            }
            model.addAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
            log.debug("Attempt to get page for create issue is successful");
            return Views.CREATE_ISSUE_VIEW;
        }
        log.debug("Periodical with id {} doesn't exist", periodicalId);
        throw new NotFoundException();
    }

    @PostMapping("/catalog/issue-create")
    public String createIssue(
            @RequestHeader(value = "referer", required = false, defaultValue = "/app/admin/catalog") String referer,
            @Valid PeriodicalIssueDTO issueDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        log.debug("Start of new issue creation");

        if (!bindingResult.hasErrors()) {
            Optional<Periodical> periodicalOpt =
                    periodicalService.findPeriodicalById(issueDTO.getPeriodicalId());
            if (!periodicalOpt.isPresent() ||
                    periodicalOpt.get().getStatus() == PeriodicalStatus.SUSPENDED) {
                log.debug("Periodical with id {} doesn't exist or has suspend status", issueDTO.getPeriodicalId());
                throw new BadRequestException();
            }
            PeriodicalIssue issue = mapper.map(issueDTO, PeriodicalIssue.class);
            boolean isCreated = issueService.addIssueToPeriodical(
                    periodicalOpt.get(), issue);
            if (isCreated) {
                log.debug("Issue was successfully created");
                return ControllerUtil.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
            } else {
                log.debug("Issue with such number already exist");
                redirectAttributes.addFlashAttribute(Attributes.ERROR_ISSUE_EXIST, true);
            }
        } else {
            log.debug("Invalid creation parameters");
            Map<String, Boolean> errors = ControllerUtil.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute(Attributes.ERRORS, errors);
        }
        redirectAttributes.addFlashAttribute(Attributes.ISSUE_DTO, issueDTO);
        log.debug("Issue creation fail");
        return ControllerUtil.redirectTo(referer);
    }

    @PostMapping("/catalog/change-status")
    public String changePeriodicalStatus(
            @RequestParam Long periodicalId,
            @RequestParam PeriodicalStatus periodicalStatus,
            @RequestHeader(value = "referer", required = false, defaultValue = "/app/admin/catalog") String referer) {
        log.debug("Start the process of changing status of the periodical");
        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);

        if (periodicalOpt.isPresent()) {
            periodicalService.changeStatus(periodicalOpt.get(), periodicalStatus);
            log.debug("Status of the periodical was successfully changed");
        } else {
            log.debug("Periodical with id {} doesn't exist. " +
                    "Changing status of the periodical failed", periodicalId);
        }

        return ControllerUtil.redirectTo(referer);
    }

    @GetMapping("/payments")
    public String getAllPaymentsPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.debug("Attempt to get all payments page");
        Page<Payment> payments = paymentService.findAllPayments(page,
                Pagination.TEN_RECORDS_PER_PAGE);
        if (payments.getTotalPages() > 0) {
            if (!payments.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PAGE,
                        payments.getTotalPages() - 1);
                return ControllerUtil.redirectTo(PagesPaths.PAYMENTS_PATH);
            }
            PageDTO<Payment> pageDTO = new PageDTO<>(payments.getContent(),
                    payments.getNumber(),
                    payments.getTotalPages());
            model.addAttribute(Attributes.PAGE_DTO, pageDTO);
        }
        return Views.PAYMENTS_VIEW;
    }

    @GetMapping("/payment")
    public String getPaymentOverviewPage(@RequestParam Long paymentId,
                                         Model model) {
        log.debug("Attempt to get a payment overview page");
        Optional<Payment> paymentOpt =
                paymentService.findPaymentById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            List<Subscription> subscription =
                    subscriptionService.findAllSubscriptionsByPayment(payment);
            model.addAttribute(Attributes.PAYMENT_DTO, payment);
            model.addAttribute(Attributes.SUBSCRIPTIONS, subscription);
            log.debug("Attempt to get a payment overview page is successful");
            return Views.PAYMENT_OVERVIEW_VIEW;
        }
        log.debug("Payment with id {} doesn't exist", paymentId);
        throw new NotFoundException();
    }

    @GetMapping("/user")
    public String gerUserPage(@RequestParam Long userId,
                              Model model) {
        log.debug("Attempt to get a user profile page");
        Optional<User> userOpt = userService.findUserById(userId);
        if (userOpt.isPresent()) {
            model.addAttribute(Attributes.USER_DTO, userOpt.get());
            log.debug("Attempt to get a user profile page is successful");
            return Views.USER_VIEW;
        }
        log.debug("User with id {} doesn't exist", userId);
        throw new NotFoundException();
    }

}
