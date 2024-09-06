package com.event.booking.services;

import org.springframework.stereotype.Service;

@Service
public class MockPaymentService {

  public boolean paymentSuccessful() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

}
