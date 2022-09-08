package com.energia.lce.controller;

import com.energia.lce.entity.Basket;
import com.energia.lce.entity.Item;
import com.energia.lce.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@SessionAttributes("basket")
@Api(value="Item Controller")
public class ItemController {


    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        model.addAttribute("food_items", itemService.findByItemType(Item.ItemType.FOOD_ITEM));
        model.addAttribute("sh_items", itemService.findByItemType(Item.ItemType.SECOND_HAND_ITEM));
        if(!model.containsAttribute("basket")) {
            Basket basket = new Basket(BigDecimal.ZERO, BigDecimal.ZERO, new HashMap<Item, Integer>());
            model.addAttribute("basket", basket);
        }
        return "items";
    }

    @GetMapping("/items/new")
    public String createItemForm(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "create_sh_item";
    }

    @PostMapping("/items")
    public String getItems(@ModelAttribute("item") Item item, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path imageDirectory = Paths.get("home","image-storage");
        File imageDirectoryFile = imageDirectory.toFile();
        if(!imageDirectoryFile.exists()) {
            imageDirectoryFile.mkdir();
        }
        Path fileNameAndPath = Paths.get(imageDirectoryFile.getPath(), file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        item.setItemImageUrl(file.getOriginalFilename());
        item.setItemType(Item.ItemType.SECOND_HAND_ITEM);
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping("/items/addToBasket/{id}")
    public String addToBasket(@PathVariable("id") String itemId, Model model) {
        Optional<Item> optionalItem = itemService.findById(Long.valueOf(itemId));
        if(optionalItem.isEmpty()) {
            throw new IllegalStateException("Could not find related");
        }
        Basket basket = (Basket) model.getAttribute("basket");
        Item selectedItem = optionalItem.get();
        BigDecimal totalPrice = basket.getTotalPrice().add(selectedItem.getItemPrice()).setScale(2, RoundingMode.FLOOR);
        basket.setTotalPrice(totalPrice);
        Integer soldQty = basket.getItemsMap().get(selectedItem);
        if(soldQty == null) {
            basket.getItemsMap().put(selectedItem, 1);
        } else {
            basket.getItemsMap().put(selectedItem, soldQty + 1);
        }
        Integer availableQuantity = selectedItem.getItemQuantity() - selectedItem.getSoldQuantity();
        if(availableQuantity <= 0) {
            return "error_not_enough_stock";
        }
        selectedItem.setSoldQuantity(selectedItem.getSoldQuantity() + 1);
        itemService.saveItem(selectedItem);

        List<Item> foodItem = itemService.findByItemType(Item.ItemType.FOOD_ITEM);
        foodItem.sort(Comparator.comparing(Item::getId));
        List<Item> shItem = itemService.findByItemType(Item.ItemType.SECOND_HAND_ITEM);
        shItem.sort(Comparator.comparing(Item::getId));
        model.addAttribute("food_items", foodItem);
        model.addAttribute("sh_items", shItem);
        model.addAttribute("basket", basket);

        return "items";
    }

    @PostMapping("/items/checkout")
    public String checkoutBasket(@ModelAttribute("basket") Basket basket, @RequestParam("paidMoney") BigDecimal paidMoney) {
        if(paidMoney.compareTo(basket.getTotalPrice()) >= 0) {
            basket.setPayBack(paidMoney.subtract(basket.getTotalPrice()).setScale(2, RoundingMode.FLOOR));
            basket.setTotalPrice(BigDecimal.ZERO);
            basket.getItemsMap().clear();
            return "redirect:/items";
        } else {
            return "insufficient_payment";
        }



    }

    @GetMapping("/items/reset")
    public String addToBasket(Model model) {
        Basket basket = (Basket) model.getAttribute("basket");
        Map<Item, Integer> itemsMap = basket.getItemsMap();

        for(Item item : itemsMap.keySet()) {
            Optional<Item> optionalItem = itemService.findById(item.getId());
            if(optionalItem.isEmpty()) {
                throw new IllegalStateException("Could not find related");
            }
            Item selectedItem = optionalItem.get();
            selectedItem.setSoldQuantity(selectedItem.getSoldQuantity() - itemsMap.get(item).intValue());
            itemService.saveItem(selectedItem);
        }

        basket  = new Basket(BigDecimal.ZERO, BigDecimal.ZERO, new HashMap<Item, Integer>());
        List<Item> foodItem = itemService.findByItemType(Item.ItemType.FOOD_ITEM);
        foodItem.sort(Comparator.comparing(Item::getId));
        List<Item> shItem = itemService.findByItemType(Item.ItemType.SECOND_HAND_ITEM);
        shItem.sort(Comparator.comparing(Item::getId));

        model.addAttribute("food_items", foodItem);
        model.addAttribute("sh_items", shItem);
        model.addAttribute("basket", basket);

        return "items";
    }

}
