package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, String> {
}
