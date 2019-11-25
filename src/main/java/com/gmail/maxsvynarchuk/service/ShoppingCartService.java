package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.service.entity.ShoppingCart;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service responsible for processing some actions
 * that has to do with shopping cart
 *
 * @author Maksym Svynarhcuk
 * @see ShoppingCart
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ShoppingCartService {
    private final PeriodicalService periodicalService;

    public boolean addItemToCart(ShoppingCart shoppingCart,
                                 User user,
                                 Periodical periodical,
                                 SubscriptionPlan subscriptionPlan) {
        log.debug("Attempt to add item to cart");
        Subscription subscription = Subscription.builder()
                .user(user)
                .periodical(periodical)
                .subscriptionPlan(subscriptionPlan)
                .build();
        return shoppingCart.addItem(subscription);
    }

    public void removeItemFromCart(ShoppingCart shoppingCart, Long cartItemId) {
        log.debug("Attempt to remove item from cart");
        shoppingCart.removeItem(cartItemId);
    }

    public void removeAllItemFromCart(ShoppingCart shoppingCart) {
        log.debug("Attempt to remove all item from cart");
        shoppingCart.removeAll();
    }

    public void updateShoppingCartItemsFromDatabase(ShoppingCart shoppingCart) {
        for (Subscription subscription : shoppingCart.getItems()) {
            Optional<Periodical> periodicalOpt =
                    periodicalService.findPeriodicalById(
                            subscription.getPeriodical().getPeriodicalId());
            if (periodicalOpt.isPresent()) {
                subscription.setPeriodical(periodicalOpt.get());
                shoppingCart.updateItem(subscription);
            } else {
                log.error("A subscription cannot refer to a non-existent periodical");
                throw new ServiceException("A subscription cannot refer to a non-existent periodical");
            }
        }
    }
}
