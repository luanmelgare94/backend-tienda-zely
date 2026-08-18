package com.tienda.zely.dto.person;

public record PersonRegisterResult(PersonRegisterStatus status, PersonResponseDto person) {

    public static PersonRegisterResult created(PersonResponseDto person) {
        return new PersonRegisterResult(PersonRegisterStatus.CREATED, person);
    }

    public static PersonRegisterResult alreadyActive() {
        return new PersonRegisterResult(PersonRegisterStatus.ALREADY_ACTIVE, null);
    }

    public static PersonRegisterResult inactiveExists() {
        return new PersonRegisterResult(PersonRegisterStatus.INACTIVE_EXISTS, null);
    }
}
