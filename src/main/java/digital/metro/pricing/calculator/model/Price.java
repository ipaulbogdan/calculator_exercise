package digital.metro.pricing.calculator.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "prices")
public class Price extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
