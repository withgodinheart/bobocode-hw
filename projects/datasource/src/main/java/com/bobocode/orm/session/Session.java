package com.bobocode.orm.session;

import com.bobocode.orm.annotation.Column;
import com.bobocode.orm.annotation.Table;
import com.bobocode.orm.exception.OrmOperationException;
import com.bobocode.orm.util.EntityKey;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Session {

    private final DataSource dataSource;
    private final Map<EntityKey<?>, Object> cache = new HashMap<>();
    private boolean isOpen = true;

    public static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE id = ?";

    public <T> T find(Class<T> entityType, Object id) {
        checkState();
        var key = new EntityKey<>(entityType, id);
        var entity = cache.computeIfAbsent(key, this::executeQuery);
        return entityType.cast(entity);
    }

    public void close() {
        isOpen = false;
        cache.clear();
    }

    private void checkState() {
        if (!isOpen) {
            throw new IllegalStateException("Cannot access already closed session.");
        }
    }

    private <T> T executeQuery(EntityKey<T> entityKey) {
        try (var connection = dataSource.getConnection()) {
            var selectQuery = prepareSelectQuery(entityKey.type());
            try (var selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setObject(1, entityKey.id());
                System.out.println("SQL: " + selectStatement);
                var resultSet = selectStatement.executeQuery();
                return createEntityFromResultSet(entityKey.type(), resultSet);
            }
        } catch (Exception e) {
            throw new OrmOperationException(String.format("Error finding entity with id = %s", entityKey.id()), e);
        }
    }

    private <T> T createEntityFromResultSet(Class<T> entityType, ResultSet rs) throws SQLException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        rs.next();
        var entity = entityType.getConstructor().newInstance();
        for (var field : entityType.getDeclaredFields()) {
            var columnAnnotation = field.getAnnotation(Column.class);
            var columnName = columnAnnotation.value();
            field.setAccessible(true);
            var columnValue = rs.getObject(columnName);
            field.set(entity, columnValue);
        }
        return entity;
    }

    private <T> String prepareSelectQuery(Class<T> entityType) {
        var tableAnnotation = entityType.getAnnotation(Table.class);
        var tableName = tableAnnotation.value();
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName);
    }
}
