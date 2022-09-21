package com.bobocode.orm.util;


public record EntityKey<T>(Class<T> type, Object id) {

}
