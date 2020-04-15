package top.theanything.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName User.java
 * @Description
 * @createTime 2020年04月15日 18:04:00
 */
@Data
@AllArgsConstructor
@Builder
@Table(name ="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String password;
}