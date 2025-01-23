package org.utils.repository;

import org.springframework.stereotype.Repository;
import org.utils.model.Bursa;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BursaRepository extends JpaRepository<Bursa, Long> {
}