package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.presentation.util.Util;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO;
import com.gmail.maxsvynarchuk.presentation.util.validator.ValidatorManager;
import com.gmail.maxsvynarchuk.service.PeriodicalService;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/app/admin")
@AllArgsConstructor
@Log4j2
public class AdminController {
    private final PeriodicalService periodicalService;

    @GetMapping("/catalog")
    public String getAdminCatalogPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            Model model) {
        log.info("Try to get admin catalog page");
        Page<Periodical> periodicals = periodicalService.findAllPeriodicals(page,
                Pagination.DEFAULT_RECORDS_PER_PAGE);
        if (periodicals.getTotalPages() > 0) {
            if (!periodicals.hasContent()) {
                String path = Util.addParameterToURI(PagesPaths.ADMIN_CATALOG_PATH,
                        RequestParameters.PAGINATION_PAGE,
                        periodicals.getTotalPages() - 1);
                return Util.redirectTo(path);
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
        model.addAttribute(Attributes.PERIODICAL_TYPES,
                periodicalService.findAllPeriodicalTypes());
        model.addAttribute(Attributes.FREQUENCIES,
                periodicalService.findAllFrequencies());
        model.addAttribute(Attributes.PUBLISHERS,
                periodicalService.findAllPublishers());

        return Views.CREATE_PERIODICAL_VIEW;
    }

    @PostMapping("/catalog/change-status")
    public String changePeriodicalStatus(@RequestParam @Min(value = 1) Long periodicalId,
                                         @RequestParam PeriodicalStatus periodicalStatus,
                                         @RequestHeader(value = "referer", required = false) String referer) {
        log.info("Start the process of changing status of the periodical");
        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);

        if (periodicalOpt.isPresent()) {
            periodicalService.changeStatus(periodicalOpt.get(), periodicalStatus);
            log.info("Status of the periodical was successfully changed");
        } else {
            log.debug("Periodical with id {} doesn't exist. " +
                    "Changing status of the periodical failed", periodicalId);
        }

        String redirectPath = Objects.nonNull(referer)
                ? referer
                : PagesPaths.ADMIN_CATALOG_PATH;
        return Util.redirectTo(redirectPath);
    }

}
