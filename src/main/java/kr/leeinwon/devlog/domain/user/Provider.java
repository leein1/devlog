package kr.leeinwon.devlog.domain.user;

public enum Provider {
    LOCAL, GOOGLE, KAKAO
    /*
    MySQL native ENUM 사용시 validate의 타입 검사와 부딪힐수 있음.
    대신 @Enumerated(EnumType.STRING) 사용시 Hibernate가 varchar를 기대함
     */
}
