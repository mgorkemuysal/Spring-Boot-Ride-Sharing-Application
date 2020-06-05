package com.project.ridesharing.service;

import com.project.ridesharing.model.Sale;
import com.project.ridesharing.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    // save a sale
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    // get all sales
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    // get a sale by sale_id
    public Sale findById(Integer sale_id) {
        return saleRepository.findById(sale_id).orElse(null);
    }

    // delete a sale by sale object
    public void delete(Sale sale) {
        saleRepository.delete(sale);
    }

}
