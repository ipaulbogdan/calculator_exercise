package digital.metro.pricing.calculator.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID publicId;
    private String name;

}
