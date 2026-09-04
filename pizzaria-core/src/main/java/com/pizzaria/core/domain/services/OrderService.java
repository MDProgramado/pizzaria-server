package com.pizzaria.core.domain.services;

import com.pizzaria.core.domain.enums.OrderStatus;
import com.pizzaria.core.domain.models.OrderItem;
import com.pizzaria.core.domain.models.Orders;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService extends Orders {

    List<OrderItem> items = new ArrayList<>();


    public String addItem(UUID produtcId, Integer quantify, BigDecimal init_price){
       if(this.getStatus() != OrderStatus.PENDING){
           throw new IllegalStateException("Não é possível alterar um pedido com status: " + this.getStatus());
       }
       OrderItem item = new OrderItem(produtcId, quantify, init_price);

       this.items.add(item);

       return "Item adicionado com sucesso!";
    }

    public BigDecimal getTotalAmount(){
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item: items){
            total = total.add((item.getSubtoal()));
        }

        return total;
    }

}
