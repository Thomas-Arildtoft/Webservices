package dk.dtu.pay.utils.models;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Qiannan
 */
 
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiatePaymentDTO {
    User merchantUser;
    String customerToken;
    BigDecimal amount;
}
