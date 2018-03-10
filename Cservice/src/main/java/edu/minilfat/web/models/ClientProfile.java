package edu.minilfat.web.models;

import java.util.UUID;

public class ClientProfile {

    private UUID privateKey;
    private UUID publicKey;


    public ClientProfile() {
        this.privateKey = UUID.randomUUID();
        this.publicKey = UUID.randomUUID();
    }

    public ClientProfile(UUID privateKey, UUID publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public UUID getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(UUID privateKey) {
        this.privateKey = privateKey;
    }

    public UUID getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(UUID publicKey) {
        this.publicKey = publicKey;
    }
}
