package at.htl.formula1.control;

import at.htl.formula1.boundary.ResultsRestClient;
import at.htl.formula1.entity.Driver;
import at.htl.formula1.entity.Race;
import at.htl.formula1.entity.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class InitBean {

    private static final String TEAM_FILE_NAME = "teams.csv";
    private static final String RACES_FILE_NAME = "races.csv";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @PersistenceContext
    EntityManager em;

    @Inject
    ResultsRestClient client;


    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        readTeamsAndDriversFromFile(TEAM_FILE_NAME);
        readRacesFromFile(RACES_FILE_NAME);
        client.readResultsFromEndpoint();

    }

    /**
     * Einlesen der Datei "races.csv" und Speichern der Objekte in der Tabelle F1_RACE
     *
     * @param racesFileName
     */

    private void readRacesFromFile(String racesFileName) {
        List<Race> races = new ArrayList<>();
        boolean temp = false;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
            .getResourceAsStream("/"+racesFileName)));
            String line;
            while((line = br.readLine()) != null){
                String[] attributes = line.split(";");
                Race race;
                if(attributes.length == 3 && temp){
                    race = new Race(Long.parseLong(attributes[0]), attributes[1], LocalDate.parse(attributes[2], formatter));
                    this.em.persist(race);
                }else{
                    temp = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Einlesen der Datei "teams.csv".
     * Das String-Array jeder einzelnen Zeile wird der Methode persistTeamAndDrivers(...)
     * übergeben
     *
     * @param teamFileName
     */
    private void readTeamsAndDriversFromFile(String teamFileName) {
        List<Team> teams = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        boolean temp = false;

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/"+teamFileName)));
            String line;
            while((line = br.readLine()) != null){
                String[] attributes = line.split(";");
                if(attributes.length == 3 && temp){
                    persistTeamAndDrivers(attributes);
                }else{
                    temp = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Es wird überprüft ob es das übergebene Team schon in der Tabelle F1_TEAM gibt.
     * Falls nicht, wird das Team in der Tabelle gespeichert.
     * Wenn es das Team schon gibt, dann liest man das Team aus der Tabelle und
     * erstellt ein Objekt (der Klasse Team).
     * Dieses Objekt wird verwendet, um die Fahrer mit Ihrem jeweiligen Team
     * in der Tabelle F!_DRIVER zu speichern.
     *
     * @param line String-Array mit den einzelnen Werten der csv-Datei
     */
    @Transactional
    private void persistTeamAndDrivers(String[] line) {
        /*
        Team team;
        Driver driver1;
        Driver driver2;

        if((team = em.createNamedQuery("Team.getByName", Team.class)
                .setParameter("NAME", line[0]).getSingleResult()) != null){
            driver1 = new Driver(line[1], team);
            driver2 = new Driver(line[2], team);
        }else{
            team = new Team(line[0]);
            em.persist(team);

            driver1 = new Driver(line[1], team);
            driver2 = new Driver(line[2], team);
        }

        em.persist(driver1);
        em.persist(driver2);
        */
    }


}
