package com.reactive.nequi.mother;

import com.reactive.nequi.model.Franchise;

import java.time.LocalDateTime;

public final class FranchiseMotherObject {
    private static final Long ID = 1L;
    private static final String NAME = "OxxO Barranquilla";
    private static final LocalDateTime CREATED_At = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public static Franchise random() {
        return new Franchise(ID, NAME, CREATED_At, UPDATED_AT);
    }

    public static Franchise randomUpdated(String newName) {
        return new Franchise(ID, newName, CREATED_At, UPDATED_AT);
    }
}
