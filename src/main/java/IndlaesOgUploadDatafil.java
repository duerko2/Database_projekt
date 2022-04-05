import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class IndlaesOgUploadDatafil {
    public static void main(String[] args) {

        // Database variable
        // -----------------------------------
        String host = "10.209.223.239"; //host is "localhost" or "127.0.0.1"
        String port = "3306"; //port is where to communicate with the RDBM system
        String database = "Idræt"; //database containing tables to be queried
        String cp = "utf8"; //Database codepage supporting danish (i.e. æøåÆØÅ)

        // Brugernavn & kodeord til database bruger.
        // -------------------------
        String username = "root";        // Username for connection
        String password = "Alex1234";    // Password for username

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;


        try {
            // Tilgå databasen med en connection
            // -----------------
            Connection connection = DriverManager.getConnection(url, username, password);

            // Kør upload metoden med den etablerede forbindelse
            //------------------
            upload(connection);

            // Close connection.
            // -----------------
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void upload(Connection connection){
        // Opret læser til datafil.
        // -----------------
        IndlaesPersonerOgTilmeldinger laeser = new IndlaesPersonerOgTilmeldinger();
        try {
            List<PersonOgTilmelding> personerOgTilmeldinger = laeser.indlaesPersonerOgTilmeldinger("tilmeldinger.csv");
            for (PersonOgTilmelding personOgTilmelding : personerOgTilmeldinger) {
                String fornavn = personOgTilmelding.getPerson().getFornavn();
                String efternavn = personOgTilmelding.getPerson().getEfternavn();
                String mail = personOgTilmelding.getPerson().getEmail();
                Date foedseldato = personOgTilmelding.getPerson().getFoedselsdato();
                String koen = personOgTilmelding.getPerson().getKoen();

                // Her skal kode indsættes, der opdaterer person tabel med informationen
                //
                //

                // Metode 1 til INSERT
                // Statement statement = connection.createStatement();
                // statement.execute("INSERT Person VALUES("+fornavn+","+efternavn+","+mail+","+foedseldato+","+koen+");");


                // Metode 2 - mere sikker
                PreparedStatement prepareStatement = connection.prepareStatement("insert into Person values(?, ?, ?, ?, ?)");
                prepareStatement.setString(1,fornavn);
                prepareStatement.setString(2,efternavn);
                prepareStatement.setString(3,mail);
                prepareStatement.setString(4, String.valueOf(foedseldato));
                prepareStatement.setString(5,koen);
                prepareStatement.execute();


                System.out.print("Person: " + personOgTilmelding.getPerson());


                if (personOgTilmelding.getTilmelding() != null) {
                    Date dato = personOgTilmelding.getTilmelding().getEventDate();
                    String eventTypeId = personOgTilmelding.getTilmelding().getEventTypeId();
                    String foreningsID = personOgTilmelding.getTilmelding().getForeningsId();

                    //Dummy tal.
                    int startNummer = 12345;
                    Time time = new Time(4,42,37);


                    // Her skal kode indsættes, der opdaterer deltager tabel med informationen
                    // Et startnummer skal genereres.
                    //
                    //

                    Statement statement2 = connection.createStatement();
                    statement2.execute("INSERT Deltager VALUES("+startNummer+","+time+","+dato+","+eventTypeId+","+foreningsID+","+fornavn+","+efternavn+","+mail+","+foedseldato+","+koen+");");


                    System.out.println("\tTilmelding: " + personOgTilmelding.getTilmelding());
                } else
                    System.out.println("\t Ingen tilhørende tilmelding");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
