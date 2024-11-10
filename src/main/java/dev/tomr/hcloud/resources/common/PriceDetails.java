package dev.tomr.hcloud.resources.common;

public class PriceDetails {
    private String gross;
    private String net;

    public PriceDetails(String gross, String net) {
        this.gross = gross;
        this.net = net;
    }

    public PriceDetails() {}

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }
}
