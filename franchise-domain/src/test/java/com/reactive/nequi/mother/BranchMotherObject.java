package com.reactive.nequi.mother;

import com.reactive.nequi.model.Branch;

import java.time.LocalDateTime;

public final class BranchMotherObject {
    private static final Long ID = 1L;
    private static final String NAME = "Prado";
    private static final Long FRANCHISE_ID = 1L;
    private static final String ADDRESS = "CL 1D 21 40";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public static Branch random() {
        return new Branch(ID, NAME, FRANCHISE_ID, ADDRESS, CREATED_AT, UPDATED_AT);
    }

    public static Branch randomUpdated(String name) {
        return new Branch(ID, name, FRANCHISE_ID, ADDRESS, CREATED_AT, UPDATED_AT);
    }
}
