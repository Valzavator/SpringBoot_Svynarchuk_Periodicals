package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;
import com.gmail.maxsvynarchuk.presentation.util.PaginationManager;
import com.gmail.maxsvynarchuk.presentation.util.Util;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO;
import com.gmail.maxsvynarchuk.service.PaymentService;
import com.gmail.maxsvynarchuk.service.PeriodicalService;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/admin")
@Validated
@AllArgsConstructor
@Log4j2
public class AdminController {
    private final PeriodicalService periodicalService;
    private final PaymentService paymentService;

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
                return Util.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
            }
            PageDTO<Periodical> pageDTO = new PageDTO<>(periodicals.getContent(),
                    periodicals.getNumber(),
                    periodicals.getTotalPages());
            model.addAttribute(Attributes.PAGE_DTO, pageDTO);
        }
        return Views.ADMIN_CATALOG_VIEW;
    }

    @GetMapping("/catalog/periodical-create")
    public String createPeriodicalPage(Model model) {
        log.debug("Attempt to get the page for creating a periodical");
        model.addAttribute(Attributes.PERIODICAL_TYPES,
                periodicalService.findAllPeriodicalTypes());
        model.addAttribute(Attributes.FREQUENCIES,
                periodicalService.findAllFrequencies());
        model.addAttribute(Attributes.PUBLISHERS,
                periodicalService.findAllPublishers());

        return Views.CREATE_PERIODICAL_VIEW;
    }

    @PostMapping("/catalog/change-status")
    public String changePeriodicalStatus(
            @RequestParam @Min(1) Long periodicalId,
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

        return Util.redirectTo(referer);
    }

    @GetMapping("/payments")
    public String getAllPaymentsPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.debug("Attempt to get all payments page");
        Page<Payment> payments = paymentService.findAllPayments(page,
                Pagination.TEN_RECORDS_PER_PAGE);
        log.error(payments.getContent().toString());

        if (payments.getTotalPages() > 0) {
            if (!payments.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PAGE,
                        payments.getTotalPages() - 1);
                return Util.redirectTo(PagesPaths.PAYMENTS_PATH);
            }
            PageDTO<Payment> pageDTO = new PageDTO<>(payments.getContent(),
                    payments.getNumber(),
                    payments.getTotalPages());
            model.addAttribute(Attributes.PAGE_DTO, pageDTO);
        }
        return Views.PAYMENTS_VIEW;
    }
}
