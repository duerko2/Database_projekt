import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
    private final String email;
    private final String fornavn;
    private final String efternavn;
    private final String koen;
    private final String foedselsdato;
    private final String by;
    private final String adresse;
    private final String postnummer;

    public Person(String email, String fornavn, String efternavn, String koen, String foedselsdato, String by, String adresse, String postnummer) {
        this.email = email;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.koen = koen;
        this.foedselsdato = foedselsdato;
        this.by=by;
        this.adresse=adresse;
        this.postnummer=postnummer;
    }

    public String getEmail() {
        return email;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getKoen() {
        return koen;
    }

    public String getFoedselsdato() {
        return foedselsdato;
    }

    public String getBy() {
        return by;
    }
    public String getAdresse(){
        return adresse;
    }

    public String getPostnummer() {
        return postnummer;
    }

    @Override
    public String toString() {
        final String D = ";";
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        return getEmail() +D +getFornavn() +D +getEfternavn() +D +getKoen() +D +getFoedselsdato()+D+getAdresse()+D+getPostnummer()+D+getBy();
    }
}

