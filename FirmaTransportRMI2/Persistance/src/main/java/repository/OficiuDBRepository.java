package repository;

import domain.Oficiu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OficiuDBRepository implements OficiuRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public OficiuDBRepository(Properties props){
        logger.info("Initializing OficiuDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public List<Oficiu> findByname(String name) {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Oficiu> oficii= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Oficii")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String nume=result.getString("nume");
                    String parola=result.getString("parola");
                    Oficiu oficiu=new Oficiu(nume,parola);
                    oficiu.setId(id);
                    if(oficiu.getNume().equals(name)){
                        oficii.add(oficiu);
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
        return oficii;

    }

    @Override
    public Iterable<Oficiu> findAll() {
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        List<Oficiu> oficii= new ArrayList<>();
        try(PreparedStatement preStm =con.prepareStatement("select * from Oficii")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String nume=result.getString("nume");
                    String parola=result.getString("parola");
                    Oficiu oficiu=new Oficiu(nume,parola);
                    oficiu.setId(id);
                    oficii.add(oficiu);
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
        return oficii;
    }

    @Override
    public void save(Oficiu entity) {
        logger.traceEntry("saving task {}", entity);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into Oficii (nume, parola) values (?,?)")){
            preStm.setString(1, entity.getNume());
            preStm.setString(2,entity.getParola());
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
    public void update(Integer integer, Oficiu entity) {

        logger.traceEntry("updating task {}", entity);
        Connection con= dbUtils.getConnection();

        try(PreparedStatement preStm=con.prepareStatement("update Oficii set nume = ?, parola=? where id=? ")){
            preStm.setString(1, entity.getNume());
            preStm.setString(2,entity.getParola());
            preStm.setInt(3,entity.getId());
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
