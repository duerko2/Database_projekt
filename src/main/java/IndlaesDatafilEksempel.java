import java.io.IOException;
import java.util.Date;
import java.util.List;

public class IndlaesDatafilEksempel {

	public static void main(String[] args) {
		IndlaesPersonerOgTilmeldinger laeser = new IndlaesPersonerOgTilmeldinger();
		try {
			List<PersonOgTilmelding> personerOgTilmeldinger = laeser.indlaesPersonerOgTilmeldinger("tilmeldinger.csv");
			for(PersonOgTilmelding personOgTilmelding : personerOgTilmeldinger) {
				String fornavn=personOgTilmelding.getPerson().getFornavn();
				String efternavn=personOgTilmelding.getPerson().getEfternavn();
				String mail=personOgTilmelding.getPerson().getEmail();
				Date foedseldato = personOgTilmelding.getPerson().getFoedselsdato();
				String koen = personOgTilmelding.getPerson().getKoen();

				// Her skal kode indsættes, der opdaterer person tabel med informationen
				//
				//
				//

				System.out.print("Person: " +personOgTilmelding.getPerson());
				if(personOgTilmelding.getTilmelding() != null){
					Date dato = personOgTilmelding.getTilmelding().getEventDate();
					String eventTypeId = personOgTilmelding.getTilmelding().getEventTypeId();
					String foreningsID = personOgTilmelding.getTilmelding().getForeningsId();


					// Her skal kode indsættes, der opdaterer deltager tabel med informationen
					// Et startnummer og skal genereres.
					//
					//


					System.out.println("\tTilmelding: " +personOgTilmelding.getTilmelding());
				}
				else
					System.out.println("\t Ingen tilhørende tilmelding");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


