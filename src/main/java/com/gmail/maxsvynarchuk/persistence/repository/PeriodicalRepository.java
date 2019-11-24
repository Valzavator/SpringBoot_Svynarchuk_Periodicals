package com.gmail.maxsvynarchuk.persistence.repository;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {

    Page<Periodical> findByPeriodicalStatusOrderByPeriodicalStatusDescPeriodicalIdDesc(
            PeriodicalStatus status, Pageable pageable);

    long countByPeriodicalStatus(PeriodicalStatus status);

}
