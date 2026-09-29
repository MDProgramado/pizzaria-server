package com.br.pizzaria.core.domain.services;

import com.br.pizzaria.core.domain.enums.OrderStatus;
import com.br.pizzaria.core.domain.models.OrderItem;
import com.br.pizzaria.core.domain.models.Orders;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class OrderService extends Orders {
    List<OrderItem> items = new ArrayList<>();


    public String addItem( Integer quantify, BigDecimal init_price){
        if(this.getStatus() != OrderStatus.PENDING){
            throw new IllegalStateException("Não é possível alterar um pedido com status: " + this.getStatus());
        }
        OrderItem item = new OrderItem(quantify, init_price);

        this.items.add(item);

        return "Item adicionado com sucesso!";
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
