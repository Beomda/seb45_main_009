package com.mainproject.server.feed.enitiy;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column
    // 0 -> 개인 or 1 -> 기업
    private boolean usertype;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @NotBlank
    private String photo_url;

//    // 이미지 여러 장
//    @Column
//    @ElementCollection
//    private List<String> photo_urls = new ArrayList<>();

    // 중복 태그
    @Column
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

//    //user와 매핑(다대일)
//    @ManyToOne
//    private User user;

//    // 말풍선 태그와 매핑(일대다)
//    @OneToMany(mappedby = "feed", cascade = CascadeType.ALL)
//    private List<Phototag> phototag;


}
