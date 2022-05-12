package com.platzi.market.domain.service;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PurchaseService{
    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    public List<Purchase> getAll(){
        return purchaseRepository.getAll();
    }
    public Optional<Purchase> getPurchase(Long purchaseId){
        return purchaseRepository.getPurchase(purchaseId);
    }
    public Optional<List<Purchase>> getByClient(String clientId){
        return purchaseRepository.getByClient(clientId);
    }
    public Purchase save(Purchase purchase){
        return purchaseRepository.save(purchase);
    }
    public boolean delete(Long purchaseId){
        return purchaseRepository.getPurchase(purchaseId).map(compra->{
            purchaseRepository.delete(purchaseId);
            return true;
        }).orElse(false);
    }

}