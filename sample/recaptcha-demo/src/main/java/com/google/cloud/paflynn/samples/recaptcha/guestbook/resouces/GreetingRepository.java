package com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GreetingRepository extends PagingAndSortingRepository<Greeting, Long> {
}
