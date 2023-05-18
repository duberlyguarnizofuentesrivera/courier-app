package com.duberlyguarnizo.courierapp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmployeeRole {
    ADMIN("Administrador"),
    SUPERVISOR("Supervisor"),
    DISPATCHER("Despachador"),
    TRANSPORTER("Transportista"),
    CLIENT("Cliente");
    public final String label;

    EmployeeRole(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}