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

    public static <E> Specification<E> emptySearch(Class<E> entityType) {
        return (root, query, cb) -> {
            return cb.and();
        };
    }

    public static <E, X> Specification<E> customDeepSearch(Class<E> rootEntityType, List<String> childFieldName,
            X fieldValue) {
        if (fieldValue.getClass().equals(String.class)) {
            return (root, query, cb) -> {
                Path<?> path = getPathByNestedFields(root, childFieldName);
                return cb.like(path.as(String.class), "%" + fieldValue + "%");
            };
        } else {
            return (root, query, cb) -> {
                Path<?> path = getPathByNestedFields(root, childFieldName);
                return cb.equal(path.as(fieldValue.getClass()), fieldValue);
            };
        }
    }

    public static Predicate equalSearch(
            CriteriaBuilder cb,
            Join<?, ?> joinResult,
            Path<?> upperLevelPath,
            String fieldName, Object fieldValue) {
        Path<?> path;
        if (joinResult != null) {
            path = joinResult.get(fieldName);
        } else {
            path = upperLevelPath.get(fieldName);
        }
        if (fieldValue.getClass().equals(String.class)) {
            return cb.like(path.as(String.class), "%" + fieldValue + "%");
        } else {
            return cb.equal(path.as(fieldValue.getClass()), fieldValue);
        }
    }

    public static <E, X extends Comparable<? super X>> Specification<E> betweenDeepSearch(Class<E> rootEntityType,
            List<String> childFieldName,
            BetweenSearchRequest<X> request) {
        return (root, query, cb) -> {
            Path<?> path = getPathByNestedFields(root, childFieldName);
            @SuppressWarnings("unchecked")
            Class<X> dataType = (Class<X>) request.getLowerBound().getClass();

            X lowerBound = request.getLowerBound();
            X upperBound = request.getUpperBound();
            return cb.between(path.as(dataType), lowerBound, upperBound);
        };
    }

    public static <E> Specification<E> foreignKeyInDeepSearch(Class<E> rootEntityType, List<String> childFieldName,
            List<BigInteger> ids) {
        return (root, query, cb) -> {
            Path<?> path = getPathByNestedFields(root, childFieldName);
            return path.get("id").as(BigInteger.class).in(ids);
        };
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
            Predicate predicate = cb.and();
            root.getJoins();
            return deepSearch(predicate, cb, root, root, searchRequest);
        };
    }

    public static Predicate deepSearch(Predicate predicate, CriteriaBuilder cb, Root<?> root, Path<?> path,
            Object searchRequest) {
        List<Field> fields = getAllFields(searchRequest.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(searchRequest);
                if (fieldValue == null) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (isSupportedEqualType(fieldType)) {
                    predicate = cb.and(predicate,
                            equalSearch(cb, null, path, field.getName(), fieldValue));
                } else if (searchRequest instanceof OneToManySearchRequest) {
                    // TODO fix the join problem;
                    // Join<?, ?> nextLevel = root.join(field.getName());
                    // predicate = cb.and(predicate,
                    //         deepSearch(predicate, cb, root, nextLevel, fieldValue));
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

    private static boolean isSupportedBetweenRangeType(Class<?> fieldType) {
        return fieldType.equals(DateBetweenSearchRequest.class) || fieldType.equals(IntegerBetweenSearchRequest.class);
    }

    private static boolean isSupportedOneToManyType(Class<?> fieldType) {
        return fieldType.equals(Ref2Filter.class);
    }

    private static Path<?> getPathByNestedFields(Root<?> root, List<String> childFieldName) {
        Path<?> path = root;
        for (int i = 0; i < childFieldName.size(); i++) {
            path = path.get(childFieldName.get(i));
        }
        return path;
    }

    private static Join<?, ?> getJoinByNestedFields(Root<?> root, List<String> childFieldName) {
        if (childFieldName.size() <= 0) {
            return null;
        }
        Join<?, ?> joinResult = root.join(childFieldName.get(0));
        for (int i = 1; i < childFieldName.size(); i++) {
            joinResult = joinResult.join(childFieldName.get(i));
        }
        return joinResult;
    }
}
