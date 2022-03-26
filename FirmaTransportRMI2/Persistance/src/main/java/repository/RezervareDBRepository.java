package repository;

import domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RezervareDBRepository implements RezervareReposytory{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RezervareDBRepository(Properties props){
        logger.info("Initializing RezevareDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Rezervare> findByNumeClient(String nume) {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Rezervare> rezervari= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Rezervari")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String numeClient=result.getString("numeClient");
                    Integer nrLocuri=result.getInt("nrLocuri");
                    Integer idCursa=result.getInt("idCursa");
                    Rezervare rezervare= new Rezervare(numeClient,nrLocuri,idCursa);
                    rezervare.setId(id);
                    if(rezervare.getNumeClient().equals(nume)){
                        rezervari.add(rezervare);
                    }
                }
            }

        } catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezervari;
    }

    @Override
    public Iterable<Rezervare> findAll() {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Rezervare> rezervari= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Rezervari")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String numeClient=result.getString("numeClient");
                    Integer nrLocuri=result.getInt("nrLocuri");
                    Integer idCursa=result.getInt("idCursa");
                    Rezervare rezervare= new Rezervare(numeClient,nrLocuri,idCursa);
                    rezervare.setId(id);
                    rezervari.add(rezervare);

                }
            }

        } catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezervari;
    }

    @Override
    public void save(Rezervare entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into Rezervari (numeClient, nrLocuri, idCursa) values (?,?,?)")){
            preStm.setString(1, entity.getNumeClient());
            preStm.setInt(2,entity.getNrLocuri());
            preStm.setInt(3,entity.getIdCursa());
            int result= preStm.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(Integer integer, Rezervare entity) {
        logger.traceEntry("updating task {}", entity);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("update Rezervari set numeClient = ?, nrLocuri=?, idCursa=? where id=? ")){
            preStm.setString(1, entity.getNumeClient());
            preStm.setInt(2,entity.getNrLocuri());
            preStm.setInt(3,entity.getIdCursa());
            preStm.setInt(4,entity.getId());
            int result= preStm.executeUpdate();
            logger.trace("Updated {} instances", result);
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
