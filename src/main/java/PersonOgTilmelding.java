import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Denne klasse repræsenterer en Person og evt. en tilhørende tilmelding fra en datafil der modtages fra Tidsmaskinens web løsning
 * 
 * Klassen er en del af projektopgaven på Kursus 02327 F22
 * 
 * @author Thorbjørn Konstantinovitz  
 *
 */
public class PersonOgTilmelding {
	private final Person person;
	private final Tilmelding tilmelding;

	public PersonOgTilmelding(String email, String fornavn, String efternavn, String koen, String foedselsdato, String by, String adresse, String postnummer, String foreningsId, String eventTypeId, String eventDato) {
		person = new Person(email, fornavn, efternavn, koen, foedselsdato,by,adresse,postnummer);
		if(foreningsId != null || eventTypeId != null || eventDato != null)
			tilmelding = new Tilmelding(foreningsId, eventTypeId, eventDato);
		else
			tilmelding = null;
	}

	public Person getPerson() {
		return person;
	}

	public Tilmelding getTilmelding() {
		return tilmelding;
	}
}
