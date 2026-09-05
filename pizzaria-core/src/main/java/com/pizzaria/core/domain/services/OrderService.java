package com.pizzaria.core.domain.services;

import com.pizzaria.core.domain.enums.OrderStatus;
import com.pizzaria.core.domain.models.OrderItem;
import com.pizzaria.core.domain.models.Orders;
import com.pizzaria.core.domain.repository.BankListItem;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService extends Orders {


     static BankListItem items = new BankListItem();


    public BankListItem addItem(Integer quantity, BigDecimal unit_price_sale){
       if(this.getStatus() != OrderStatus.PENDING){
           throw new IllegalStateException("Não é possível alterar um pedido com status: " + this.getStatus());
       }
       BankListItem item = new BankListItem();

      return BankListItem.save(item);
    }

    public String removeItem(Integer quantify, BigDecimal init_price) {
        if (items == null){
            System.out.println("Não é possivel remover um item da lista");
        }
        OrderItem item = new OrderItem(quantify, init_price);

        this.items.remove(item);

        return "Item excluído com sucesso!";
    }

    public BigDecimal getTotalAmount(){
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item: items){
            total = total.add((item.getSubtoal()));
        }

        return total;
    }

}
