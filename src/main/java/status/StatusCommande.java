package status;

import java.util.Objects;

public class StatusCommande {
    private String platOuBoisson = "";
    private String status = EnumStatus.APREPARER.getAffichage();

    public StatusCommande() {};

    public StatusCommande(String platOuBoisson_) {
        platOuBoisson = platOuBoisson_;
    }

    public String getPlatOuBoisson() {
        return platOuBoisson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void nextStatus() {
        if (Objects.equals(status, EnumStatus.APREPARER.getAffichage())) status = EnumStatus.ENCOURS.getAffichage();
        else if (Objects.equals(status, EnumStatus.ENCOURS.getAffichage())) status = EnumStatus.ASERVIR.getAffichage();
        else if (Objects.equals(status, EnumStatus.ASERVIR.getAffichage())) status = EnumStatus.TERMINE.getAffichage();
    }
}
