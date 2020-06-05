package com.project.ridesharing.controller;

import com.project.ridesharing.model.Sale;
import com.project.ridesharing.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    SaleService saleService;

    // save a sale
    @PostMapping("/sale/save")
    public Sale save(@Validated @RequestBody Sale sale) {
        return saleService.save(sale);
    }

    // get all sales
    @GetMapping("/sale/all")
    public List<Sale> findAll() {
        return saleService.findAll();
    }

    // get a sale by sale_id
    @GetMapping("/sale/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable(value = "id") Integer sale_id) {
        Sale sale = saleService.findById(sale_id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sale);
    }

    // update a sale by sale_id
    @PutMapping("/sale/update/{id}")
    public ResponseEntity<Sale> updateSaleById(@PathVariable(value = "id") Integer sale_id, @Validated @RequestBody Sale new_sale) {
        Sale sale = saleService.findById(sale_id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        sale.setTotal_payment(new_sale.getTotal_payment());
        sale.setPaid(new_sale.isPaid());
        sale.setActive(new_sale.isActive());
        Sale updated_sale = saleService.save(sale);
        return ResponseEntity.ok().body(updated_sale);
    }

    // delete a sale by sale_id
    @DeleteMapping("/sale/delete/{id}")
    public ResponseEntity<Sale> deleteSaleById(@PathVariable(value = "id") Integer sale_id) {
        Sale sale = saleService.findById(sale_id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        saleService.delete(sale);
        return ResponseEntity.ok().build();
    }

}
