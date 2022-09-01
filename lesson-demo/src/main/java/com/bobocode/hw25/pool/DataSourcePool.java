package com.bobocode.hw25.pool;

import lombok.Getter;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataSourcePool extends PGSimpleDataSource {

    private final transient Queue<Connection> pool;
    @Getter
    private final int poolSize;

    public DataSourcePool(String url, String user, String password, int poolSize) {
        setURL(url);
        setUser(user);
        setPassword(password);

        Preconditions.checkArgument(poolSize > 0, "poolSize");
        this.poolSize = poolSize;

        this.pool = new ConcurrentLinkedQueue<>();
        initPool();
    }

    @SneakyThrows
    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            pool.add(new ConnectionProxy(super.getConnection(), pool));
        }
    }

    @Override
    public Connection getConnection() {
        return pool.poll();
    }
}
