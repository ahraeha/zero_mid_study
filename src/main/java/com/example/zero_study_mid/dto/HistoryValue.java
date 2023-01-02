package com.example.zero_study_mid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // @AllArgsConstructor는 모든 변수를 사용하는 생성자를 자동완성 시켜주는 어노테이션이다.
@NoArgsConstructor // @NoArgsConstructor는 어떠한 변수도 사용하지 않는 기본 생성자를 자동완성 시켜주는 어노테이션이다.
@Builder // @Builder 어노테이션을 활용하면 해당 클래스의 객체의 생성에 Builder패턴을 적용시켜준다. 모든 변수들에 대해 build하기를 원한다면 클래스 위에
// @Builder를 붙이면 되지만, 특정 변수만을 build하기 원한다면 생성자를 작성하고 그 위에 @Builder 어노테이션을 붙여주면 된다.

@Data //@Data 어노테이션을 활용하면 @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 자동완성 시켜준다.

public class HistoryValue {
    private int Id;
    private String LAT;
    private String LNT;
    private String Date;
}
