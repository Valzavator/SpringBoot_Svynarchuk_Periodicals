package com.gmail.maxsvynarchuk.persistence.repository;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Periodical, Long> {

}
