package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalIssue;
import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;
import com.gmail.maxsvynarchuk.presentation.exception.NotFoundException;
import com.gmail.maxsvynarchuk.presentation.util.Util;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO;
import com.gmail.maxsvynarchuk.service.IssueService;
import com.gmail.maxsvynarchuk.service.PeriodicalService;
import com.gmail.maxsvynarchuk.service.SubscriptionService;
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
@RequestMapping("/app")
@Validated
@AllArgsConstructor
@Log4j2
public class PeriodicalController {
    private final PeriodicalService periodicalService;
    private final SubscriptionService subscriptionService;
    private final IssueService issueService;

    @GetMapping("/catalog")
    public String getCatalogPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.debug("Attempt to get catalog page");
        Page<Periodical> periodicals =
                periodicalService.findAllPeriodicalsByStatus(PeriodicalStatus.ACTIVE,
                        page,
                        Pagination.FIVE_RECORDS_PER_PAGE);

        if (periodicals.getTotalPages() > 0) {
            if (!periodicals.hasContent()) {
                redirectAttributes.addAttribute(RequestParameters.PAGINATION_PAGE,
                        periodicals.getTotalPages() - 1);
                return Util.redirectTo(PagesPaths.CATALOG_PATH);
            }

            List<SubscriptionPlan> subscriptionPlans =
                    subscriptionService.findAllSubscriptionPlans();
            model.addAttribute(Attributes.SUBSCRIPTION_PLANS, subscriptionPlans);
            PageDTO<Periodical> pageDTO = new PageDTO<>(periodicals.getContent(),
                    periodicals.getNumber(),
                    periodicals.getTotalPages());
            model.addAttribute(Attributes.PAGE_DTO, pageDTO);
        }
        return Views.CATALOG_VIEW;
    }

    @GetMapping("/periodical")
    public String getPeriodicalPage(@RequestParam @Min(1) Long periodicalId,
                                    Model model) {
        log.debug("Attempt to get page for overview periodical");
        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);
        if (periodicalOpt.isPresent()) {
            List<SubscriptionPlan> subscriptionPlans =
                    subscriptionService.findAllSubscriptionPlans();
            List<PeriodicalIssue> periodicalIssues =
                    issueService.findAllIssuesByPeriodical(periodicalOpt.get());
            model.addAttribute(Attributes.SUBSCRIPTION_PLANS, subscriptionPlans);
            model.addAttribute(Attributes.PERIODICAL, periodicalOpt.get());
            model.addAttribute(Attributes.ISSUES, periodicalIssues);
            log.debug("Attempt to get page for overview periodical is successful");
            return Views.PERIODICAL_VIEW;
        }
        throw new NotFoundException("Periodical doesn't exist");
    }
}
