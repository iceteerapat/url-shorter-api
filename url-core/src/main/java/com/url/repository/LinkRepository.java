package com.url.repository;

import com.url.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByCustomerNoOrderByCreateDateDesc(String customerNo);

    Link findByShortUrlOrderByCreateDateDesc(String shortUrl);

    Link findByLongUrlOrderByCreateDateDesc(String longUrl);
}
