package dk.dtu.pay.utils.models;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qiannan
 */
 
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    User user;
    int numberOfTokens;
}
