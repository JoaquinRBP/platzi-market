package com.platzi.market.web.controller;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll(){
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/client/{id}")
    public ResponseEntity<List<Purchase>> getByClient(@PathVariable(name = "id") String clientID){
        return purchaseService.getByClient(clientID).map(clientes->new ResponseEntity<>(clientes,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchase(Long purchaseId){
        return purchaseService.getPurchase(purchaseId).map(compra->new ResponseEntity<>(compra,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase){
        return new ResponseEntity<>(purchaseService.save(purchase),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity delete(@PathVariable(name = "id") Long purchaseId){
        if(purchaseService.delete(purchaseId)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
