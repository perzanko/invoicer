package invoicer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition="TEXT")
    private String comment;

    private Date createdAt;

    private Date updatedAt;

    public Date getDate() {
        return this.date;
    }

    public String getComment() {
        return this.comment;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    Integer getId() {
        return this.id;
    }

    void setDate(Date date) {
        this.date = date;
    }

    void setComment(String comment) {
        this.comment = comment;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}