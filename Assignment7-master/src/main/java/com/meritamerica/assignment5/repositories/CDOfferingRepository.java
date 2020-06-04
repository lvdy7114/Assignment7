package com.meritamerica.assignment5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.assignment5.models.CDOffering;


@Repository
public interface CDOfferingRepository extends JpaRepository<CDOffering , Integer>{

}
