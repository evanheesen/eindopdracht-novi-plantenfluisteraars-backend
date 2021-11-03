package nl.evanheesen.plantenfluisteraars.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GardenKey implements Serializable {

    @Column(name = "customer_id")
    Long customerId;

    @Column(name = "employee_id")
    Long employeeId;

    public Long getCustomerId() {
        return this.customerId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GardenKey)) return false;
        final GardenKey other = (GardenKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$customerId = this.getCustomerId();
        final Object other$customerId = other.getCustomerId();
        if (this$customerId == null ? other$customerId != null : !this$customerId.equals(other$customerId)) return false;
        final Object this$employeeId = this.getEmployeeId();
        final Object other$employeeId = other.getEmployeeId();
        if (this$employeeId == null ? other$employeeId != null : !this$employeeId.equals(other$employeeId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GardenKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $customerId = this.getCustomerId();
        result = result * PRIME + ($customerId == null ? 43 : $customerId.hashCode());
        final Object $employeeId = this.getEmployeeId();
        result = result * PRIME + ($employeeId == null ? 43 : $employeeId.hashCode());
        return result;
    }

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
