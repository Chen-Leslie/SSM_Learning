package pojo;

import java.util.List;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-20
 */
public class Class {
    private Integer cId;
    private String cName;
    private List<Student> students;

    public Class() {
    }

    public Class(Integer cId, String cName, List<Student> students) {
        this.cId = cId;
        this.cName = cName;
        this.students = students;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Class{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", students=" + students +
                '}';
    }
}
