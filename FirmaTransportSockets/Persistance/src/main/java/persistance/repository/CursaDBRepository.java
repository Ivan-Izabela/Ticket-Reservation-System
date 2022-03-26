package persistance.repository;

import domain.Cursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaDBRepository implements CursaRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CursaDBRepository(Properties props){
        logger.info("Initializing CursaDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Iterable<Cursa> findAll() {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Cursa> curse= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Curse")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String destinatie=result.getString("destinatie");
                    LocalDateTime plecare= LocalDateTime.parse(result.getString("plecare"));
                    Integer locuriDisponibile= result.getInt("locuriDisponibile");
                    Cursa cursa= new Cursa(destinatie,plecare,locuriDisponibile);
                    cursa.setId(id);
                    curse.add(cursa);
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
        return curse;
    }

    @Override
    public void save(Cursa entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into Curse (destinatie, plecare, locuriDisponibile) values (?,?,?)")){
            preStm.setString(1, entity.getDestinatie());
            preStm.setString(2, entity.getPlecare().toString());
            preStm.setInt(3, entity.getLocuriDisponibile());
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
    public void update(Integer integer, Cursa entity) {
        logger.traceEntry("updating task {}", entity);
        Connection con= dbUtils.getConnection();

        try(PreparedStatement preStm=con.prepareStatement("update Curse set destinatie = ?, plecare = ?, locuriDisponibile = ? where id = ? ")){
            preStm.setString(1, entity.getDestinatie());
            preStm.setString(2,  entity.getPlecare().toString());
            preStm.setInt(3, entity.getLocuriDisponibile());
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

    @Override
    public List<Cursa> findByDestinatie(String destinatie) {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Cursa> curse= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Curse")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String destinatieN=result.getString("destinatie");
                    LocalDateTime plecareN= LocalDateTime.parse(result.getString("plecare"));
                    Integer locuriDisponibileN= result.getInt("locuriDisponibile");
                    Cursa cursa= new Cursa(destinatieN,plecareN,locuriDisponibileN);
                    cursa.setId(id);
                    if(cursa.getDestinatie().equals(destinatie)){
                        curse.add(cursa);
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
        return curse;
    }

    @Override
    public List<Cursa> findByPlecare(LocalDateTime plecare) {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Cursa> curse= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Curse")){

            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){

                    int id= result.getInt("id");
                    String destinatieN=result.getString("destinatie");
                    LocalDateTime plecareN= LocalDateTime.parse(result.getString("plecare"));
                    Integer locuriDisponibileN= result.getInt("locuriDisponibile");
                    Cursa cursa= new Cursa(destinatieN,plecareN,locuriDisponibileN);
                    cursa.setId(id);
                    if(cursa.getPlecare().equals(plecare)){
                        curse.add(cursa);
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
        return curse;
    }

    @Override
    public Cursa findByID(Integer id) {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        System.out.println(id);

        try(PreparedStatement preStm =con.prepareStatement("select * from Curse where id=?")){
            preStm.setInt(1,id);
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){

                    int idN= result.getInt("id");
                    String destinatieN=result.getString("destinatie");
                    LocalDateTime plecareN= LocalDateTime.parse(result.getString("plecare"));
                    Integer locuriDisponibileN= result.getInt("locuriDisponibile");
                    Cursa cursa= new Cursa(destinatieN,plecareN,locuriDisponibileN);
                    cursa.setId(idN);


                        System.out.println(cursa);
                        return cursa;

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
        return null;
    }
}
