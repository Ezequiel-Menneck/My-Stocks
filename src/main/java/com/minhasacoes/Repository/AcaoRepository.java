package com.minhasacoes.Repository;

import com.minhasacoes.Entities.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoRepository extends JpaRepository<Stocks, Integer> {
}
