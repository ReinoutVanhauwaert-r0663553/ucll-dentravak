package be.ucll.da.Model;

public enum BreadType {
    TURKISHBREAD("Turkish Bread"),WRAP("Wrap"),BOTERHAMMEKE("Boterhammeke");



    private String breadtype;

    BreadType(String breadtype){
        this.breadtype = breadtype;
    }
    public String getBreadtype() {
        return breadtype;
    }
}
