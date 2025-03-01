package com.mikitellurium.telluriumforge.energy;

import net.minecraftforge.energy.EnergyStorage;

public abstract class SimpleEnergyStorage extends EnergyStorage {

    public SimpleEnergyStorage(int capacity) {
        super(capacity);
    }

    public SimpleEnergyStorage(int capacity, int maxReceive) {
        super(capacity, maxReceive);
    }

    public SimpleEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public SimpleEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receivedEnergy = super.receiveEnergy(maxReceive, simulate);
        if (receivedEnergy != 0) {
            onEnergyChanged();
        }

        return receivedEnergy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (extractedEnergy != 0) {
            onEnergyChanged();
        }

        return extractedEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public abstract void onEnergyChanged();

}
