package com.energia.lce.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@ApiModel(value="Item Api Doc",description="Model")
public class Item {

    public enum ItemType {
        FOOD_ITEM, SECOND_HAND_ITEM
    }
    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    @ApiModelProperty(value="Item ID")
    private Long id;
    @ApiModelProperty(value="Item Name")
    private String itemName;
    @ApiModelProperty(value="Item Image URL")
    private String itemImageUrl;
    @ApiModelProperty(value="Item Type")
    private ItemType itemType;
    @ApiModelProperty(value="Item Price")
    private BigDecimal itemPrice;
    @ApiModelProperty(value="Item Quantity")
    private Integer itemQuantity;
    @ApiModelProperty(value="Sold Quantity")
    private Integer soldQuantity = 0;

    public Item() {

    }
    public Item(String itemName, String itemImageUrl, ItemType itemType, BigDecimal itemPrice, Integer itemQuantity, Integer soldQuantity) {
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.soldQuantity = soldQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (id != item.getId()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result;
        return result;
    }
}
