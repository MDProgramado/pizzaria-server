package com.pizzaria.core.domain.repository;

import com.pizzaria.core.domain.models.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankListItem {
    private static final List<OrderItem> listaItens = new ArrayList<>();

    public static List<OrderItem> getAllOrderItem(){
        return listaItens;
    }

    public static OrderItem getItemId(UUID id){
        return listaItens.stream()
                .filter(ingredients -> ingredients.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static boolean save(Integer quantity, BigDecimal unit_price_sale){
        OrderItem Item = new OrderItem(quantity,unit_price_sale);
        return  listaItens.add(Item);
    }

    public static boolean removeId(UUID id){
          OrderItem orderItem = getItemId(id);
          if (orderItem != null){

              listaItens.remove(orderItem);

              System.out.println("Excluído!");

              return true;
          }
            return false;
    }


}
