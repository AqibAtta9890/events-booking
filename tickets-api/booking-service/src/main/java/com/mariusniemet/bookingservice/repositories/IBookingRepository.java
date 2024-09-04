package com.mariusniemet.bookingservice.repositories;

import com.mariusniemet.bookingservice.entities.Booking;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {


  Optional<Booking> findByUserEmailAndEventName(String userEmail , String eventName);

}
