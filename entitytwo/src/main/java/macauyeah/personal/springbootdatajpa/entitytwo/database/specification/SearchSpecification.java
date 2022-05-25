package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification {
    public static Logger LOG = LoggerFactory.getLogger(SearchSpecification.class);

    public static Predicate equalSearch(
            CriteriaBuilder cb,
            Path<?> path, Object fieldValue) {
        if (fieldValue.getClass().equals(String.class)) {
            return cb.like(path.as(String.class), "%" + fieldValue + "%");
        } else {
            return cb.equal(path.as(fieldValue.getClass()), fieldValue);
        }
    }

    public static <X extends Comparable<? super X>> Predicate betweenDeepSearch(
            CriteriaBuilder cb,
            Path<?> path,
            BetweenSearchRequest<X> request) {
        @SuppressWarnings("unchecked")
        Class<X> dataType = (Class<X>) request.getLowerBound().getClass();

        X lowerBound = request.getLowerBound();
        X upperBound = request.getUpperBound();
        return cb.between(path.as(dataType), lowerBound, upperBound);
    }

    public static Predicate foreignKeyInDeepSearch(
            CriteriaBuilder cb,
            Path<?> path,
            ForeignKeyInSearchRequest request) {
        return path.get("id").as(BigInteger.class).in(request.getIn());
    }

    public static <S> List<Field> getAllFields(Class<S> searchRequestType) {
        Field[] fieldArray = searchRequestType.getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        if (fieldArray != null && fieldArray.length > 0) {
            fields.addAll(Arrays.asList(fieldArray));
        }
        if (!searchRequestType.equals(Object.class)) {
            fields.addAll(getAllFields(searchRequestType.getSuperclass()));
        }
        return fields;
    }

    public static <E, S> Specification<E> deepSearchAllFields(Class<E> rootEntityType, S searchRequest) {
        return (root, query, cb) -> {
            return deepSearch(cb, root, null, searchRequest);
        };
    }

    public static Predicate deepSearch(CriteriaBuilder cb, Root<?> root, Join<?, ?> joinObj,
            Object searchRequest) {
        Predicate predicate = cb.and();
        List<Field> fields = getAllFields(searchRequest.getClass());
        Path<?> path;
        if (joinObj != null) {
            path = joinObj;
        } else {
            path = root;
        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(searchRequest);
                if (fieldValue == null) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (fieldValue instanceof JoinSearchRequest) { // only case to extend the path with join
                    Join<?, ?> joinResult;
                    if (joinObj != null) {
                        joinResult = joinObj.join(field.getName());
                    } else {
                        joinResult = root.join(field.getName());
                    }
                    predicate = cb.and(predicate,
                            deepSearch(cb, null, joinResult, fieldValue));
                } else if (isSupportedEqualType(fieldType)) {
                    predicate = cb.and(predicate,
                            equalSearch(cb, path.get(field.getName()), fieldValue));
                } else if (fieldValue instanceof BetweenSearchRequest) {
                    predicate = cb.and(predicate,
                            betweenDeepSearch(cb, path.get(field.getName()), (BetweenSearchRequest<?>) fieldValue));
                } else if (fieldValue instanceof ForeignKeyInSearchRequest) {
                    predicate = cb.and(predicate,
                            foreignKeyInDeepSearch(cb, path.get(field.getName()),
                                    (ForeignKeyInSearchRequest) fieldValue));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOG.warn(e.getMessage());
                e.printStackTrace();
            }
        }
        return predicate;
    }

    private static boolean isSupportedEqualType(Class<?> fieldType) {
        return fieldType.equals(String.class) ||
                fieldType.equals(Date.class) ||
                fieldType.equals(Boolean.class) ||
                fieldType.equals(Integer.class);
    }
}
