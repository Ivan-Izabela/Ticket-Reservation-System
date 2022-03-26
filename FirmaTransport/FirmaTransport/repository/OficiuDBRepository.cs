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
    class OficiuDBRepository : IOficiuRemository
    {
        private static readonly ILog log = LogManager.GetLogger("OficiuDBRepository");

        IDictionary<String, string> props;

        public OficiuDBRepository(IDictionary<String, string> props)
        {

            log.Info("Creare OficiuDBRepository");
            this.props = props;
        }

        public IEnumerable<Oficiu> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Oficiu> oficii = new List<Oficiu>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = " select *from Oficii";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        string parola = dataR.GetString(2);

                        Oficiu oficiu = new Oficiu(id, nume, parola);
                        oficii.Add(oficiu);
                    }
                }
            }
            return oficii;

            //throw new NotImplementedException();
        }

        public Oficiu findByName(string nume)
        {
            IDbConnection con = DBUtils.getConnection(props);
            //IList<Oficiu> oficii = new List<Oficiu>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Oficii";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string nume1 = dataR.GetString(1);
                        string parola = dataR.GetString(2);

                        Oficiu oficiu = new Oficiu(id, nume1, parola);
                        if (oficiu.nume.Equals(nume))
                        {
                            return oficiu;
                        }                       
                    }
                }
            }
            return null;

            //throw new NotImplementedException();
        }

        public Oficiu findOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Oficii where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1= dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        string parola = dataR.GetString(0);

                        Oficiu oficiu = new Oficiu(id1, nume,parola);

                        log.InfoFormat("Exiting findOne with value {0}", oficiu);
                        return oficiu;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void save(Oficiu entity)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Oficii values (@id, @nume, @parola)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramNume= comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.nume;
                comm.Parameters.Add(paramNume);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@parola";
                paramPassword.Value = entity.parola;
                comm.Parameters.Add(paramPassword);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No office added !");
            }
        }
    }
}
