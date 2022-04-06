import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class IndlaesOgUploadDatafil {
    public static void main(String[] args) {

        // Database variable
        // -----------------------------------
        String host = "127.0.0.1"; //host is "localhost" or "127.0.0.1"
        String port = "3306"; //port is where to communicate with the RDBM system
        String database = "Tidsmaskinen"; //database containing tables to be queried
        String cp = "utf8"; //Database codepage supporting danish (i.e. æøåÆØÅ)

        // Brugernavn & kodeord til database bruger.
        // -------------------------
        String username = "root";        // Username for connection
        String password = "dererentyr2";    // Password for username

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
            List<PersonOgTilmelding> personerOgTilmeldinger = laeser.indlaesPersonerOgTilmeldinger("Tilmeldingers.csv");
            for (PersonOgTilmelding personOgTilmelding : personerOgTilmeldinger) {
                String fornavn = personOgTilmelding.getPerson().getFornavn();
                String efternavn = personOgTilmelding.getPerson().getEfternavn();
                String mail = personOgTilmelding.getPerson().getEmail();
                String foedseldato = personOgTilmelding.getPerson().getFoedselsdato();
                String koen = personOgTilmelding.getPerson().getKoen();
                String adresse = personOgTilmelding.getPerson().getAdresse();
                String by = personOgTilmelding.getPerson().getBy();
                String postnummer = personOgTilmelding.getPerson().getPostnummer();

                // Her skal kode indsættes, der opdaterer person tabel med informationen
                //
                //

                // Metode 1 til INSERT
                //Statement statement = connection.createStatement();
                //statement.execute("INSERT Person VALUES("+fornavn+","+efternavn+","+mail+","+foedseldato+","+koen+");");


                try{
                    // Insert i personpost
                    PreparedStatement prepareStatement2 = connection.prepareStatement("INSERT INTO PersonPostBy VALUES(?,?);");
                    prepareStatement2.setString(1,postnummer);
                    prepareStatement2.setString(2,by);
                    prepareStatement2.execute();
                } catch(Exception e){
                    System.out.println((e.getMessage()));
                }


                // Metode 2 - mere sikker
                try {
                    PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?, ?);");
                    prepareStatement.setString(1, mail);
                    prepareStatement.setString(2, fornavn);
                    prepareStatement.setString(3, efternavn);
                    prepareStatement.setString(4, adresse);
                    prepareStatement.setString(5, postnummer);
                    prepareStatement.setString(6, foedseldato);
                    prepareStatement.setString(7, koen);
                    prepareStatement.execute();
                }catch(Exception e){
                    System.out.println((e.getMessage()));
                }






                System.out.print("Person: " + personOgTilmelding.getPerson());


                if (personOgTilmelding.getTilmelding() != null) {

                    // Hvis personen også deltager i et event
                    // --------------------------------------

                    String dato = personOgTilmelding.getTilmelding().getEventDate();
                    String eventTypeId = personOgTilmelding.getTilmelding().getEventTypeId();
                    String foreningsID = personOgTilmelding.getTilmelding().getForeningsId();

                    //Dummy tal eller null.
                    String startNummer = null;
                    java.sql.Time time = null;


                    // Her skal kode indsættes, der opdaterer deltager tabel med informationen
                    // Et startnummer skal genereres?
                    //
                    //

                    try {
                        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Person Deltager(?, ?, ?, ?, ?, ?);");
                        statement2.setString(1, startNummer);
                        statement2.setObject(2, time);
                        statement2.setString(3, dato);
                        statement2.setString(4, eventTypeId);
                        statement2.setString(5, foreningsID);
                        statement2.setString(6, mail);
                        // Kan køres når databasen er opdateret med foreninger og events.

                        //statement2.execute();

                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }



                    // Dårlige metode.
                    // ---------------
                    //Statement statement2 = connection.createStatement();
                    //statement2.execute("INSERT Deltager VALUES("+startNummer+","+time+","+dato+","+eventTypeId+","+foreningsID+","+mail+");");


                    System.out.println("\tTilmelding: " + personOgTilmelding.getTilmelding());
                } else
                    System.out.println("\t Ingen tilhørende tilmelding");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
