package com.api.eventmanagement.repositories;

import com.api.eventmanagement.entities.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {


  Optional<Event> findByName(String name);
}
