package br.com.harbitech.school.subcategory;

import br.com.harbitech.school.category.Category;
import br.com.harbitech.school.course.Course;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static br.com.harbitech.school.validation.ValidationUtil.validateUrl;

@Entity
@NamedQuery(name = "Subcategory.allActive", query = "SELECT s FROM Subcategory s WHERE s.status = :status ORDER BY " +
        "s.orderVisualization")
@NamedQuery(name = "Subcategory.AllWithoutDescription", query = "SELECT s.name FROM Subcategory s WHERE s.description = '' " +
        "OR s.description IS NULL")
public class Subcategory implements Comparable<Subcategory> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String codeUrl;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String studyGuide;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM")
    private SubCategoryStatus status = SubCategoryStatus.INACTIVE;
    private int orderVisualization = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "subcategory")
    @NotNull(message = "{subcategory.course.required}")
    private List<Course> courses = new ArrayList<>();

    @Deprecated
    public Subcategory(){}

    public Subcategory(String name, String codeUrl, Category category){
        Assert.hasText(name, "{subcategory.name.required}");
        Assert.hasText(codeUrl, "{subcategory.codeUrl.required}");
        Assert.notNull(category, "{subcategory.category.required}");
        validateUrl(codeUrl, "{subcategory.codeUrl.pattern}" + codeUrl);

        this.name = name;
        this.codeUrl = codeUrl;
        this.category = category;
        this.status = SubCategoryStatus.INACTIVE;
        this.orderVisualization = -1;
    }

    public Subcategory(String name, String codeUrl, int orderVisualization, String description, String studyGuide,
                       SubCategoryStatus status, Category category) {
        this(name, codeUrl, category);
        this.description = description;
        this.studyGuide = studyGuide;
        this.status = status;
        this.orderVisualization = orderVisualization;
    }

    public Subcategory(Long id,String name, String codeUrl, int orderVisualization, String description, String studyGuide,
                       SubCategoryStatus status, Category category){
        this(name,codeUrl,orderVisualization,description,studyGuide,status,category);
        this.id = id;
    }

    public int getOrderVisualization() {
        return orderVisualization;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public SubCategoryStatus getStatus() {
        return status;
    }

    public String getStudyGuide() {
        return studyGuide;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Category getCategory() {
        return category;
    }

    public String getStatusDescription(){
        return this.status.getDescription();
    }

    public String getCategoryCodeUrl(){
        return this.category.getCodeUrl();
    }


    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                ", description='" + description + '\'' +
                ", studyGuide='" + studyGuide + '\'' +
                ", status=" + status +
                ", orderVisualization=" + orderVisualization +
                ", category=" + category +
                '}';
    }

    @Override
    public int compareTo(Subcategory otherSubCategory) {
        if (otherSubCategory.getOrderVisualization() < this.orderVisualization) {
            return otherSubCategory.getOrderVisualization();
        }
        return this.orderVisualization;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}