public class Camera {
    private int cameraId;
    private String nr;
    private int supliment;
    private String dotari;
    private int tipId;

    public Camera(String nr, int supliment, String dotari, int tipId) {
        this.nr = nr;
        this.supliment = supliment;
        this.dotari = dotari;
        this.tipId = tipId;
    }

    public int getCameraId() {
        return cameraId;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public int getSupliment() {
        return supliment;
    }

    public void setSupliment(int supliment) {
        this.supliment = supliment;
    }


    public String getDotari() {
        return dotari;
    }

    public void setDotari(String dotari) {
        this.dotari = dotari;
    }

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }
}
