// This file is licensed under the Elastic License 2.0. Copyright 2021-present, StarRocks Inc.

package com.starrocks.sql.optimizer.base;

import java.util.Objects;

/**
 * PropertySet represents a set of required properties
 */
public class PhysicalPropertySet {
    private SortProperty sortProperty;
    private DistributionProperty distributionProperty;
    private CTEProperty cteProperty;

    public static final PhysicalPropertySet EMPTY = new PhysicalPropertySet();

    public PhysicalPropertySet() {
        this(DistributionProperty.EMPTY, SortProperty.EMPTY, CTEProperty.EMPTY);
    }

    public PhysicalPropertySet(DistributionProperty distributionProperty) {
        this(distributionProperty, SortProperty.EMPTY, CTEProperty.EMPTY);
    }

    public PhysicalPropertySet(SortProperty sortProperty) {
        this(DistributionProperty.EMPTY, sortProperty, CTEProperty.EMPTY);
    }

    public PhysicalPropertySet(DistributionProperty distributionProperty, SortProperty sortProperty) {
        this(distributionProperty, sortProperty, CTEProperty.EMPTY);
    }

    public PhysicalPropertySet(DistributionProperty distributionProperty, SortProperty sortProperty,
                               CTEProperty cteProperty) {
        this.distributionProperty = distributionProperty;
        this.sortProperty = sortProperty;
        this.cteProperty = cteProperty;
    }

    public SortProperty getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(SortProperty sortProperty) {
        this.sortProperty = sortProperty;
    }

    public DistributionProperty getDistributionProperty() {
        return distributionProperty;
    }

    public void setDistributionProperty(DistributionProperty distributionProperty) {
        this.distributionProperty = distributionProperty;
    }

    public CTEProperty getCteProperty() {
        return cteProperty;
    }

    public void setCteProperty(CTEProperty cteProperty) {
        this.cteProperty = cteProperty;
    }

    public boolean isSatisfy(PhysicalPropertySet other) {
        return sortProperty.isSatisfy(other.sortProperty) &&
                distributionProperty.isSatisfy(other.distributionProperty);
    }

    public PhysicalPropertySet copy() {
        return new PhysicalPropertySet(distributionProperty, sortProperty, cteProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortProperty, distributionProperty, cteProperty);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PhysicalPropertySet)) {
            return false;
        }

        final PhysicalPropertySet other = (PhysicalPropertySet) obj;
        return this.sortProperty.equals(other.sortProperty) &&
                this.distributionProperty.equals(other.distributionProperty) &&
                this.cteProperty.equals(other.cteProperty);
    }

    @Override
    public String toString() {
        return sortProperty.getSpec().getOrderDescs().toString() +
                ", " + distributionProperty.getSpec() + ", " + cteProperty.toString();
    }
}
