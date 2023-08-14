/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Tienda.entities;

import com.tienda.entities.Product;

public class Item extends Product {
    
    private int cantidad; 
    public Item () {
        
    }
    
    public Item (Product product) {
        super.setId(product.getId());
        super.setCategory(product.getCategory());
        super.setDescripcion(product.getDescripcion());
        super.setDetalle(product.getDetalle());
        super.setPrecio(product.getPrecio());
        super.setExistencias(product.getExistencias());
        super.setRuta_imagen(product.getRuta_imagen());
        this.cantidad = 0;
    }
}
