package com.ocmsbd.demo.service;


import com.ocmsbd.demo.service.filter.Filter;
import com.ocmsbd.demo.service.filter.RangeFilter;
import com.ocmsbd.demo.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

@Transactional(
        readOnly = true
)
public abstract class QueryService<ENTITY> {
    public QueryService() {
    }

    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn((Function)metaclassFunction, filter.getIn());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified(metaclassFunction, filter.getSpecified()) : null;
        }
    }

    protected Specification<ENTITY> buildStringSpecification(StringFilter filter, SingularAttribute<? super ENTITY, String> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected Specification<ENTITY> buildSpecification(StringFilter filter, Function<Root<ENTITY>, Expression<String>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn((Function)metaclassFunction, filter.getIn());
        } else if (filter.getContains() != null) {
            return this.likeUpperSpecification(metaclassFunction, filter.getContains());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified(metaclassFunction, filter.getSpecified()) : null;
        }
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(field);
        });
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn((Function)metaclassFunction, filter.getIn());
        } else {
            Specification<ENTITY> result = Specification.where((Specification)null);
            if (filter.getSpecified() != null) {
                result = result.and(this.byFieldSpecified(metaclassFunction, filter.getSpecified()));
            }

            if (filter.getGreaterThan() != null) {
                result = result.and(this.greaterThan(metaclassFunction, filter.getGreaterThan()));
            }

            if (filter.getGreaterThanOrEqual() != null) {
                result = result.and(this.greaterThanOrEqualTo(metaclassFunction, filter.getGreaterThanOrEqual()));
            }

            if (filter.getLessThan() != null) {
                result = result.and(this.lessThan(metaclassFunction, filter.getLessThan()));
            }

            if (filter.getLessThanOrEqual() != null) {
                result = result.and(this.lessThanOrEqualTo(metaclassFunction, filter.getLessThanOrEqual()));
            }

            return result;
        }
    }

    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<? super OTHER, X> valueField) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(reference).get(valueField);
        });
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter, SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField) {
        return this.buildSpecification(filter, (root) -> {
            return root.get(reference).get(valueField);
        });
    }

   /* protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, SetAttribute<ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField) {
        return this.buildReferringEntitySpecification(filter, (root) -> {
            return root.join(reference);
        }, (entity) -> {
            return entity.get(valueField);
        });
    }*/

    protected <OTHER, MISC, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter, Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity, Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        if (filter.getEquals() != null) {
            return this.equalsSpecification(functionToEntity.andThen(entityToColumn), filter.getEquals());
        } else {
            return filter.getSpecified() != null ? this.byFieldSpecified((root) -> {
                return (SetJoin)functionToEntity.apply(root);
            }, filter.getSpecified()) : null;
        }
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter, SetAttribute<ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField) {
        return this.buildReferringEntitySpecification(filter, (root) -> {
            return root.join(reference);
        }, (entity) -> {
            return entity.get(valueField);
        });
    }

    protected <OTHER, MISC, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter, Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity, Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        Function<Root<ENTITY>, Expression<X>> fused = functionToEntity.andThen(entityToColumn);
        if (filter.getEquals() != null) {
            return this.equalsSpecification(fused, filter.getEquals());
        } else if (filter.getIn() != null) {
            return this.valueIn((Function)fused, filter.getIn());
        } else {
            Specification<ENTITY> result = Specification.where((Specification)null);
            if (filter.getSpecified() != null) {
                result = result.and(this.byFieldSpecified((root) -> {
                    return (SetJoin)functionToEntity.apply(root);
                }, filter.getSpecified()));
            }

            if (filter.getGreaterThan() != null) {
                result = result.and(this.greaterThan(fused, filter.getGreaterThan()));
            }

            if (filter.getGreaterThanOrEqual() != null) {
                result = result.and(this.greaterThanOrEqualTo(fused, filter.getGreaterThanOrEqual()));
            }

            if (filter.getLessThan() != null) {
                result = result.and(this.lessThan(fused, filter.getLessThan()));
            }

            if (filter.getLessThanOrEqual() != null) {
                result = result.and(this.lessThanOrEqualTo(fused, filter.getLessThanOrEqual()));
            }

            return result;
        }
    }

    protected <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.equal((Expression)metaclassFunction.apply(root), value);
        };
    }

    /** @deprecated */
    protected <X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, X> field, X value) {
        return this.equalsSpecification((root) -> {
            return root.get(field);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<? super OTHER, X> valueField, X value) {
        return this.equalsSpecification((root) -> {
            return root.get(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> equalsSetSpecification(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> idField, X value) {
        return this.equalsSpecification((root) -> {
            return root.join(reference).get(idField);
        }, value);
    }

    protected Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> {
            return builder.like(builder.upper((Expression)metaclassFunction.apply(root)), this.wrapLikeQuery(value));
        };
    }

    /** @deprecated */
    protected Specification<ENTITY> likeUpperSpecification(SingularAttribute<? super ENTITY, String> field, String value) {
        return this.likeUpperSpecification((root) -> {
            return root.get(field);
        }, value);
    }

    protected <X> Specification<ENTITY> byFieldSpecified(Function<Root<ENTITY>, Expression<X>> metaclassFunction, boolean specified) {
        return specified ? (root, query, builder) -> {
            return builder.isNotNull((Expression)metaclassFunction.apply(root));
        } : (root, query, builder) -> {
            return builder.isNull((Expression)metaclassFunction.apply(root));
        };
    }

    /** @deprecated */
    @Deprecated
    protected <X> Specification<ENTITY> byFieldSpecified(SingularAttribute<? super ENTITY, X> field, boolean specified) {
        return this.byFieldSpecified((root) -> {
            return root.get(field);
        }, specified);
    }

    protected <X> Specification<ENTITY> byFieldEmptiness(Function<Root<ENTITY>, Expression<Set<X>>> metaclassFunction, boolean specified) {
        return specified ? (root, query, builder) -> {
            return builder.isNotEmpty((Expression)metaclassFunction.apply(root));
        } : (root, query, builder) -> {
            return builder.isEmpty((Expression)metaclassFunction.apply(root));
        };
    }

    /** @deprecated */
    @Deprecated
    protected <X> Specification<ENTITY> byFieldSpecified(SetAttribute<ENTITY, X> field, boolean specified) {
        return this.byFieldEmptiness((root) -> {
            return root.get(field);
        }, specified);
    }

    protected <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction, Collection<X> values) {
        return (root, query, builder) -> {
            In<X> in = builder.in((Expression)metaclassFunction.apply(root));

            X value;
            for(Iterator var6 = values.iterator(); var6.hasNext(); in = in.value(value)) {
                value = (X) var6.next();
            }

            return in;
        };
    }

    /** @deprecated */
    @Deprecated
    protected <X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, X> field, Collection<X> values) {
        return this.valueIn((root) -> {
            return root.get(field);
        }, values);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<? super OTHER, X> valueField, Collection<X> values) {
        return this.valueIn((root) -> {
            return root.get(reference).get(valueField);
        }, values);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.greaterThanOrEqualTo((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.greaterThan((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo((Expression)metaclassFunction.apply(root), value);
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> {
            return builder.lessThan((Expression)metaclassFunction.apply(root), value);
        };
    }

    /** @deprecated */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(SingularAttribute<? super ENTITY, X> field, X value) {
        return this.greaterThanOrEqualTo((root) -> {
            return root.get(field);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(SingularAttribute<? super ENTITY, X> field, X value) {
        return this.greaterThan((root) -> {
            return root.get(field);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SingularAttribute<? super ENTITY, X> field, X value) {
        return this.lessThanOrEqualTo((root) -> {
            return root.get(field);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(SingularAttribute<? super ENTITY, X> field, X value) {
        return this.lessThan((root) -> {
            return root.get(field);
        }, value);
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> valueIn(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, Collection<X> values) {
        return this.valueIn((root) -> {
            return root.join(reference).get(valueField);
        }, values);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.greaterThan((root) -> {
            return root.get(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.greaterThan((root) -> {
            return root.join(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.greaterThanOrEqualTo((root) -> {
            return root.get(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.greaterThanOrEqualTo((root) -> {
            return root.join(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.lessThan((root) -> {
            return root.get(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.lessThan((root) -> {
            return root.join(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SingularAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.lessThanOrEqualTo((root) -> {
            return root.get(reference).get(valueField);
        }, value);
    }

    /** @deprecated */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SetAttribute<? super ENTITY, OTHER> reference, SingularAttribute<OTHER, X> valueField, X value) {
        return this.lessThanOrEqualTo((root) -> {
            return root.join(reference).get(valueField);
        }, value);
    }
}
