package top.theanything.forum.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName Article.java
 * @Description
 * @createTime 2020年04月15日 21:19:00
 */
@Data
@Getter
@Setter
@Builder
public class Article {

    private String poster;
    private String title;
    private String link;
    private String time;
    private String votes;
}
