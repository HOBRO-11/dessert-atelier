package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Addresses;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.AddressQueryDslRepo;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Long>, AddressQueryDslRepo {

}
