package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Addresses;
import com.yangnjo.dessert_atelier.repository.query_dsl.AddressQueryDslRepo;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Long>, AddressQueryDslRepo {

}
