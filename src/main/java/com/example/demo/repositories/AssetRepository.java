package com.example.demo.repositories;

import com.example.demo.models.Asset;
import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByCustomerAndAssetName(Customer customer, String assetName);

    List<Asset> findByCustomer(Customer customer);
}