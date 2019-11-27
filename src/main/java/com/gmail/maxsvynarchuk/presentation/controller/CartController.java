package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.util.constants.*;
import com.gmail.maxsvynarchuk.service.ShoppingCartService;
import com.gmail.maxsvynarchuk.service.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/app")
@SessionAttributes("cart")
@AllArgsConstructor
@Log4j2
public class CartController {
    ShoppingCartService shoppingCartService;

//    @ModelAttribute("cart")
//    public ShoppingCart cart() {
//        return new ShoppingCart();
//    }

    @GetMapping("/cart")
    public String getCartPage(@ModelAttribute ShoppingCart cart) {
        log.info("Try to get shopping cart page");
        log.error(cart);
        shoppingCartService.updateShoppingCartItemsFromDatabase(cart);
        return Views.CART_VIEW;
    }

}
