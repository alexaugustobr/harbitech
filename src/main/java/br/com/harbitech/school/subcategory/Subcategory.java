package br.com.harbitech.school.subcategory;

import br.com.harbitech.school.category.Category;
import br.com.harbitech.school.course.Course;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "O nome da subcategoria não pode estar em branco.")
    @Size(max = 70, message = "Ops! O nome de uma subcategoria não pode ter mais do que 70 caracteres")
    private String name;
    @NotBlank(message = "O código da subcategoria não pode estar em branco.")
    @Size(max = 70, message = "Ops! O código de uma subcategoria não pode ter mais do que 70 caracteres")
    @Pattern(regexp = "[-a-z]+", message = "O código da url do curso está incorreto (só aceita letras minúsculas e hífen)")
    private String codeUrl;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String studyGuide;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM")
    @NotNull
    private SubCategoryStatus status = SubCategoryStatus.INACTIVE;
    private int orderVisualization;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "subcategory")
    @NotNull(message = "A subcategoria precisa ter um curso associado.")
    private List<Course> courses = new ArrayList<>();

    @Deprecated
    public Subcategory(){}

    public Subcategory(String name, String codeUrl, Category category){
        Assert.hasText(name, "O nome da sub-categoria não pode estar em branco.");
        Assert.hasText(codeUrl, "O código da URL da sub-categoria não pode estar em branco.");
        Assert.notNull(category, "A sub-categoria deve ter uma categoria associada.");
        validateUrl(codeUrl, "O código da url da sub-categoria está incorreto (só aceita letras minúsculas e hífen): " + codeUrl) ;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudyGuide(String studyGuide) {
        this.studyGuide = studyGuide;
    }

    public void setOrderVisualization(int orderVisualization) {
        this.orderVisualization = orderVisualization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public void setStatus(SubCategoryStatus status) {
        this.status = status;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
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
