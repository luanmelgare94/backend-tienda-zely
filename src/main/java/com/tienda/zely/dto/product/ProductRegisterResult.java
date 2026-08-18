package com.tienda.zely.dto.product;

public record ProductRegisterResult(ProductRegisterStatus status, ProductResponseDto product) {

    public static ProductRegisterResult created(ProductResponseDto product) {
        return new ProductRegisterResult(ProductRegisterStatus.CREATED, product);
    }

    public static ProductRegisterResult alreadyActive() {
        return new ProductRegisterResult(ProductRegisterStatus.ALREADY_ACTIVE, null);
    }

    public static ProductRegisterResult inactiveExists() {
        return new ProductRegisterResult(ProductRegisterStatus.INACTIVE_EXISTS, null);
    }
}
