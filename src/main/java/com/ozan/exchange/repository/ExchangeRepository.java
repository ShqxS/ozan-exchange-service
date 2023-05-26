package com.ozan.exchange.repository;

import com.ozan.exchange.domain.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ExchangeRepository {

    private final MongoTemplate mongoTemplate;

    public CurrencyExchange save(CurrencyExchange currencyExchange) {
        return mongoTemplate.save(currencyExchange);
    }

    public Optional<CurrencyExchange> findByTransactionId(String transactionId) {
        return Optional.ofNullable(mongoTemplate.findById(transactionId, CurrencyExchange.class));
    }

    public List<CurrencyExchange> findAllExchangesByTimeRange(Integer page, Integer size, LocalDateTime startDate, LocalDateTime endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdAt").gte(startDate).lte(endDate));
        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);
        query.with(Sort.by(Sort.Direction.ASC, "createdAt"));

        return mongoTemplate.find(query, CurrencyExchange.class);
    }

}
