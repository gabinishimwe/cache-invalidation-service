package com.cacheinvalidation.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebeziumEventPayload {
    private Object before;
    private Object after;
    private Source source;
    private String op;
    @JsonProperty("ts_ms")
    private Long tsMs;
    @JsonProperty("ts_us")
    private Long tsUs;
    @JsonProperty("ts_ns")
    private Long tsNs;
    private Optional<Transaction> transaction;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Source {
        private String version;
        private String connector;
        private String name;
        @JsonProperty("ts_ms")
        private long tsMs;
        private String snapshot;
        private String db;
        private String sequence;
        @JsonProperty("ts_us")
        private String tsUs;
        @JsonProperty("ts_ns")
        private String tsNs;
        private String schema;
        private String table;
        private Long txId;
        private Long lsn;
        private Long xmin;

        // Getters and setters
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction {
        private String id;
        @JsonProperty("total_order")
        private long totalOrder;
        @JsonProperty("data_collection_order")
        private long dataCollectionOrder;

        // Getters and setters
    }
}
