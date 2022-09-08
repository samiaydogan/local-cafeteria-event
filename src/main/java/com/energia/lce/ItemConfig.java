package com.energia.lce;

import com.energia.lce.entity.Item;
import com.energia.lce.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class ItemConfig {

    @Bean
    CommandLineRunner commandLineRunner(ItemRepository itemRepository) {
        return args -> {
            Item brownieItem = new Item("Brownie", "brownie.jpeg", Item.ItemType.FOOD_ITEM, BigDecimal.valueOf(0.65), 48, 0);
            Item muffinItem = new Item("Muffin", "muffin.jpeg", Item.ItemType.FOOD_ITEM, BigDecimal.valueOf(1.00), 36, 0);
            Item cakePopItem = new Item("Cake Pop", "cake_pop.jpeg", Item.ItemType.FOOD_ITEM, BigDecimal.valueOf(1.35), 24, 0);
            Item appleTartItem = new Item("Apple Tart", "apple_tart.jpeg", Item.ItemType.FOOD_ITEM, BigDecimal.valueOf(1.50), 60, 0);
            Item waterItem = new Item("Water", "water.jpeg", Item.ItemType.FOOD_ITEM, BigDecimal.valueOf(1.50), 30, 0);

            itemRepository.saveAll(List.of(brownieItem, muffinItem, cakePopItem, appleTartItem, waterItem));
        };
    }
}
