package com.mariusniemet.eventsservice.repositories;

import com.mariusniemet.eventsservice.entities.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {


  Optional<Event> findByName(String name);
}
