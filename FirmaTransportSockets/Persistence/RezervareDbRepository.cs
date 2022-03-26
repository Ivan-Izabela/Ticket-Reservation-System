using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;
using log4net;
using System.Data;

namespace Persistence
{
    public class RezervareDBRepository : IRezervareRepository
    {
        private static readonly ILog log = LogManager.GetLogger("RezervareDBRepository");

        IDictionary<String, string> props;

        public RezervareDBRepository()
        {

            log.Info("Creare RezervareDBRepository");
            //this.props = props;
        }

        public IEnumerable<Rezervare> findAll()
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Rezervare> rezervari = new List<Rezervare>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = " select *from Rezervari";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int locuri  = dataR.GetInt32(2);
                        int idCursa = dataR.GetInt32(3);

                        Rezervare rezervare = new Rezervare(id, nume, locuri, idCursa);
                        rezervari.Add(rezervare);

                        
                    }
                }
            }
            return rezervari;
        }

        public IEnumerable<Rezervare> findByIdCursa(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Rezervare> rezervari = new List<Rezervare>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Rezervari";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int locuri = dataR.GetInt32(2);
                        int idCursa = dataR.GetInt32(3);

                        Rezervare rezervare = new Rezervare(id1, nume, locuri, idCursa);
                        if (rezervare.idCursa.Equals(id))
                        {
                            rezervari.Add(rezervare);
                        }

                    }
                }
            }
            return rezervari;
        }

        public Rezervare findOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Rezervari where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int locuri = dataR.GetInt32(2);
                        int idCursa = dataR.GetInt32(3);

                        Rezervare rezervare = new Rezervare(id1, nume, locuri, idCursa);

                        log.InfoFormat("Exiting findOne with value {0}", rezervare);
                        return rezervare;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public int getNewId()
        {
            IDbConnection con = DBUtils.getConnection();
            int result = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select MAX(id) from Rezervari";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        result = dataR.GetInt32(0);

                        return result + 1;
                    }
                }

                return 1;
            }
        }


        public void save(Rezervare entity)
        {
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Rezervari values (@id, @numeClient, @nrLocuri, @idCursa)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@numeClient";
                paramNume.Value = entity.numeClient;
                comm.Parameters.Add(paramNume);

                var paramNrLocuri = comm.CreateParameter();
                paramNrLocuri.ParameterName = "@nrlocuri";
                paramNrLocuri.Value = entity.nrLocuri;
                comm.Parameters.Add(paramNrLocuri);

                var paramIdCursa = comm.CreateParameter();
                paramIdCursa.ParameterName = "@idCursa";
                paramIdCursa.Value = entity.idCursa;
                comm.Parameters.Add(paramIdCursa);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No office added !");
            }

        }

    }
}
