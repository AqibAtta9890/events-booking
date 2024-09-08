package com.event.booking.repositories;

import com.event.booking.entities.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {


  Optional<Booking> findByUserEmailAndEventName(String userEmail , String eventName);

  List<Booking> findByUserEmail(String userEmail);

}
