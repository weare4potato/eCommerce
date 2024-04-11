package com.potato.ecommerce.domain.category.entity;

public enum CategoryType {
    ELECTRONIC_APPLIANCES("가전제품"),
    FASHION_CLOTHING("패션의류"),
    BEAUTY("뷰티"),
    LARGE_APPLIANCES("대형가전"),
    SMALL_APPLIANCES("소형가전"),
    WOMENS_CLOTHING("여성의류"),
    MENS_CLOTHING("남성의류"),
    UNISEX_CLOTHING("남여공용"),
    MAKEUP("메이크업"),
    HAIR("헤어"),
    BODY("바디"),
    WASHING_MACHINE("세탁기"),
    REFRIGERATOR("냉장고"),
    TELEVISION("TV"),
    HAIR_DRYER("드라이기"),
    VACUUM_CLEANER("청소기"),
    TOP("상의"),
    BOTTOM("하의"),
    SKINCARE("스킨케어"),
    BASE_MAKEUP("베이스"),
    EYE_MAKEUP("아이"),
    LIP("립"),
    SHAMPOO("샴푸"),
    RINSE("린스"),
    BODY_WASH("바디워시"),
    BODY_LOTION("바디로션");

    private final String description;

    CategoryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
