package com.example.demo.models;

import java.util.List;

public record Connection<E>(PageInfo pageInfo, List<Edge<E>> edges) {
}
