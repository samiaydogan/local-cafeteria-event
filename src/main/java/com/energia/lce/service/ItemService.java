package com.energia.lce.service;

import com.energia.lce.entity.Item;
import com.energia.lce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findByItemType(Item.ItemType itemType) {
        return itemRepository.findByItemType(itemType);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

}
