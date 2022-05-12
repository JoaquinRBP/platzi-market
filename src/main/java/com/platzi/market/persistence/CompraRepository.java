package com.platzi.market.persistence;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.repository.PurchaseRepository;
import com.platzi.market.persistence.crud.CompraCrudRepository;
import com.platzi.market.persistence.entity.Compra;
import com.platzi.market.persistence.mapper.PurchaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository{
    private CompraCrudRepository compraCrudRepository;
    private PurchaseMapper purchaseMapper;

    public CompraRepository(CompraCrudRepository compraCrudRepository,PurchaseMapper purchaseMapper) {
        this.compraCrudRepository = compraCrudRepository;
        this.purchaseMapper=purchaseMapper;
    }

    @Override
    public List<Purchase> getAll() {
            return purchaseMapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId).map(compras->purchaseMapper.toPurchases(compras));
    }

    @Override
    public Optional<Purchase> getPurchase(Long purchaseId) {
        return compraCrudRepository.findById(purchaseId).map(compra -> purchaseMapper.toPurchase(compra));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);
        compra.getComprasProductos().forEach(productos->productos.setCompra(compra));
        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }

    @Override
    public void delete(Long purchaseId) {
        compraCrudRepository.deleteById(purchaseId);
    }
}
