package com.ufrpe.ppgia.quantumapp;

import android.widget.ImageView;

/**
 * Created by leonardo on 7/26/17.
 */

public class Qubit {
    private ImageView qubitImageView;

    public Qubit(ImageView qubitImageView) {
        this.qubitImageView = qubitImageView;
    }

    public ImageView getQubitImageView() {
        return qubitImageView;
    }
}
