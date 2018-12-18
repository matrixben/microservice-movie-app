package jason.luo.domain;

import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.math.BigDecimal;

@Alias(value = "user_class")
public class UserClass implements Serializable {
    private int classId;
    private String className;
    private BigDecimal classDiscount;
    private boolean classIsactive;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BigDecimal getClassDiscount() {
        return classDiscount;
    }

    public void setClassDiscount(BigDecimal classDiscount) {
        this.classDiscount = classDiscount;
    }

    public boolean isClassIsactive() {
        return classIsactive;
    }

    public void setClassIsactive(boolean classIsactive) {
        this.classIsactive = classIsactive;
    }
}
