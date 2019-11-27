package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.util.Util;
import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.service.PeriodicalService;
import com.gmail.maxsvynarchuk.service.ShoppingCartService;
import com.gmail.maxsvynarchuk.service.SubscriptionService;
import com.gmail.maxsvynarchuk.service.entity.ShoppingCart;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/cart")
@SessionAttributes("shoppingCart")
@AllArgsConstructor
@Log4j2
public class CartController {
    private final PeriodicalService periodicalService;
    private final SubscriptionService subscriptionService;
    private final ShoppingCartService shoppingCartService;

    @ModelAttribute("shoppingCart")
    public ShoppingCart cart() {
        return new ShoppingCart();
    }

    @GetMapping
    public String getCartPage(@ModelAttribute("shoppingCart") ShoppingCart cart) {
        log.debug("Attempt to get shopping cart page");
        shoppingCartService.updateShoppingCartItemsFromDatabase(cart);
        return Views.CART_VIEW;
    }

    @PostMapping("/add")
    public String addItemToCart(
            @RequestParam @Min(1) Long periodicalId,
            @RequestParam @Min(1) Integer subscriptionPlanId,
            @SessionAttribute("user") User user,
            @ModelAttribute("shoppingCart") ShoppingCart shoppingCart,
            @RequestHeader(value = "referer", required = false, defaultValue = "/app/catalog") String referer,
            RedirectAttributes redirectAttributes) {
        log.debug("Attempt to add item to shopping cart");

        Optional<Periodical> periodicalOpt =
                periodicalService.findPeriodicalById(periodicalId);
        SubscriptionPlan subscriptionPlan =
                subscriptionService.findSubscriptionPlanById(subscriptionPlanId)
                        .orElseThrow(BadRequestException::new);

        if (!periodicalOpt.isPresent() ||
                periodicalOpt.get().getStatus() == PeriodicalStatus.SUSPENDED) {
            redirectAttributes.addFlashAttribute(Attributes.ERROR_PERIODICAL_INVALID, true);
            log.debug("Invalid periodical with id {} for subscription", periodicalId);
            return Util.redirectTo(referer);
        }

        Periodical periodical = periodicalOpt.get();
        boolean isAlreadySubscribed =
                subscriptionService.isAlreadySubscribed(user, periodical);
        if (isAlreadySubscribed) {
            redirectAttributes.addFlashAttribute(Attributes.ERROR_IS_ALREADY_SUBSCRIBED, true);
            log.debug("User is already subscribed to periodical with id {}", periodicalId);
            return Util.redirectTo(referer);
        }

        boolean isAddedToCart = shoppingCartService.addItemToCart(shoppingCart, user,
                periodical, subscriptionPlan);
        if (!isAddedToCart) {
            redirectAttributes.addFlashAttribute(Attributes.ERROR_IS_ALREADY_IN_CART, true);
            log.debug("Item already exists in cart");
        } else {
            log.debug("Item successfully added to cart");
        }

        return Util.redirectTo(referer);
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam @Min(1) Long cartItemId,
                                     @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        log.debug("Attempt to remove item from shopping cart");
        shoppingCartService.removeItemFromCart(shoppingCart, cartItemId);
        return Util.redirectTo(PagesPaths.CART_PATH);
    }

    @PostMapping("/remove-all")
    public String removeAllItemsFromCart(@ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        log.debug("Attempt to remove all items from shopping cart");
        shoppingCartService.removeAllItemFromCart(shoppingCart);
        return Util.redirectTo(PagesPaths.CART_PATH);
    }

    @PostMapping("/subscription-payment")
    public String subscriptionPayment(@SessionAttribute("user") User user,
                                      @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        log.debug("Attempt to process new subscriptions");
        shoppingCartService.updateShoppingCartItemsFromDatabase(shoppingCart);

        List<Subscription> subscriptions = shoppingCart.getItems();
        BigDecimal totalPrice = shoppingCart.getTotalPrice();

        try {
            subscriptionService.processSubscriptions(user, subscriptions, totalPrice);
        } catch (ServiceException exception) {
            log.error(exception.getMessage());
            return Util.redirectTo(PagesPaths.CART_PATH);
        }

        shoppingCartService.removeAllItemFromCart(shoppingCart);
        log.debug("New subscriptions processed successfully");
        return Util.redirectTo(PagesPaths.CART_PATH);
    }
}
