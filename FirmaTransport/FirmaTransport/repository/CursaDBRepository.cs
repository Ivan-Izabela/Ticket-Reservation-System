using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FirmaTransport.domain;
using log4net;
using System.Data;

namespace FirmaTransport.repository
{
    class CursaDBRepository : ICursaRepository
    {
        private static readonly ILog log = LogManager.GetLogger("CursaDBRepository");

        IDictionary<String, string> props;

        public CursaDBRepository(IDictionary<String, string> props)
        {

            log.Info("Creare CursaDBRepository");
            this.props = props;
        }

        public IEnumerable<Cursa> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Cursa> curse = new List<Cursa>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = " select *from Curse";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string destinatie = dataR.GetString(1);
                        DateTime plecare = DateTime.Parse(dataR.GetString(2));
                        int locuri = dataR.GetInt32(3);
                        Cursa cursa = new Cursa(id, destinatie, plecare, locuri);
                        curse.Add(cursa);
                    }
                }
            }
            return curse;
        }

        public IList<Cursa> findByDestinatie(string destinatie)
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Cursa> curse = new List<Cursa>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Curse";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string destinatie1 = dataR.GetString(1);
                        DateTime plecare = DateTime.Parse(dataR.GetString(2));
                        int locuri = dataR.GetInt32(3);
                        Cursa cursa = new Cursa(id, destinatie1, plecare, locuri);
                        if (cursa.destinatie.Equals(destinatie))
                        {
                            curse.Add(cursa);
                        }
                        
                    }
                }
            }
            return curse;
        }

        public Cursa findOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Curse where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {

                        int id1 = dataR.GetInt32(0);
                        string destinatie1 = dataR.GetString(1);
                        DateTime plecare = DateTime.Parse(dataR.GetString(2));
                        int locuri = dataR.GetInt32(3);
                        Cursa cursa = new Cursa(id1, destinatie1, plecare, locuri);

                        log.InfoFormat("Exiting findOne with value {0}", cursa);
                        return cursa;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void save(Cursa entity)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Curse values (@id, @destinatie, @plecare, @locuriDisponibile)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramDestinatie = comm.CreateParameter();
                paramDestinatie.ParameterName = "@destinatie";
                paramDestinatie.Value = entity.destinatie;
                comm.Parameters.Add(paramDestinatie);

                var paramPlecare = comm.CreateParameter();
                paramPlecare.ParameterName = "@plecare";
                paramPlecare.Value =entity.plecare.ToString();
                comm.Parameters.Add(paramPlecare);

                var paramLocuri = comm.CreateParameter();
                paramLocuri.ParameterName = "@locuriDisponibile";
                paramLocuri.Value = entity.locuriDisponibile;
                comm.Parameters.Add(paramLocuri);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No office added !");
            }
        }

        public void update(int id, Cursa entity)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Curse set destinatie = @destinatie, plecare = @plecare, locuriDisponibile = @locuriDisponibile Where id = @id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramDestinatie = comm.CreateParameter();
                paramDestinatie.ParameterName = "@destinatie";
                paramDestinatie.Value = entity.destinatie;
                comm.Parameters.Add(paramDestinatie);

                var paramPlecare = comm.CreateParameter();
                paramPlecare.ParameterName = "@plecare";
                paramPlecare.Value = entity.plecare.ToString();
                comm.Parameters.Add(paramPlecare);

                var paramLocuri = comm.CreateParameter();
                paramLocuri.ParameterName = "@locuriDisponibile";
                paramLocuri.Value = entity.locuriDisponibile;
                comm.Parameters.Add(paramLocuri);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No office added !");
            }
        }
    }
}
