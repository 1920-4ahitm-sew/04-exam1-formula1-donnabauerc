package at.htl.formula1.entity;

import javax.persistence.*;

/**
 * Formula1 - Result
 * <p>
 * The id's are assigned by the database.
 */
@Entity
@Table(name = "F1_RESULT")
@NamedQueries({
        @NamedQuery(
                name = "Result.getPointsSumOfDriver",
                query = "select sum(r.points) from Result r where r.driver = :DRIVER"
        ),
        @NamedQuery(
                name = "Result.findWinnerOfRace",
                query = "select r1.driver from Result r1 where r1.race = :RACE " +
                "and r1.points >= all(select max(r2.points) from Result r2 where r2.race=r1.race)"
        ),
        @NamedQuery(
                name = "Result.findRacesWonByTeam",
                query = "select r.race from Result r where r.driver.team = :TEAM and r.position=1"
        ),
        @NamedQuery(
                name = "Result.getPointsOfAllDrivers",
                query = "select r.driver.name, sum(r.points) from Result r group by r.driver.name"
        )
})
public class Result {

    @Transient
    public int[] pointsPerPosition = {0, 25, 18, 15, 12, 10, 8, 6, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    private Race race;
    private int position;
    private int points;
    @ManyToOne
    private Driver driver;


    //region Constructors
    public Result() {
    }

    public Result(Race race, int position, Driver driver) {
        this.setRace(race);
        this.setPosition(position);
        this.setDriver(driver);
    }
    //endregion


    //region Getter and Setter

    public Long getId() {
        return id;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        this.points = pointsPerPosition[position];
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    //endregion


    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", race=" + race.getCountry() +
                ", position=" + position +
                ", points=" + points +
                ", driver=" + driver.getName() +
                '}';
    }
}
