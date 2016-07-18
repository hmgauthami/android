package com.ascendlearning.jbl.uglys.models;

/**
 * Created by sonal.agarwal on 6/8/2016.
 */
public class FLCMotorHpData {
    private String hp;
    private String motor_ampere;
    private int breaker_size;
    private String starter_size;
    private String heater_amps;
    private String wire_size;
    private String conduit_size;

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getMotor_ampere() {
        return motor_ampere;
    }

    public void setMotor_ampere(String motor_ampere) {
        this.motor_ampere = motor_ampere;
    }

    public int getBreaker_size() {
        return breaker_size;
    }

    public void setBreaker_size(int breaker_size) {
        this.breaker_size = breaker_size;
    }

    public String getStarter_size() {
        return starter_size;
    }

    public void setStarter_size(String starter_size) {
        this.starter_size = starter_size;
    }

    public String getHeater_amps() {
        return heater_amps;
    }

    public void setHeater_amps(String heater_amps) {
        this.heater_amps = heater_amps;
    }

    public String getWire_size() {
        return wire_size;
    }

    public void setWire_size(String wire_size) {
        this.wire_size = wire_size;
    }

    public String getConduit_size() {
        return conduit_size;
    }

    public void setConduit_size(String conduit_size) {
        this.conduit_size = conduit_size;
    }
}
