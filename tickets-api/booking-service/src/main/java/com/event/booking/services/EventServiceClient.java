package com.event.booking.services;

import com.event.booking.dtos.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "EVENT-SERVICE",url = "http://localhost:7003")
public interface EventServiceClient {

  @GetMapping(value = "/api/v1/events/{name}")
  EventDto getEventDetail(@PathVariable("name") String name,
      @RequestParam("ticketType") String ticketType);


  @PutMapping(value = "/api/v1/events/{id}")
  void updateEvent(@PathVariable("id") Long id ,@RequestBody EventDto eventDto);


}
