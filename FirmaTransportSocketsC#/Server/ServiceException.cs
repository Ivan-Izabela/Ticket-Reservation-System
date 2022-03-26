using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace Services
{
    public class ServiceException : Exception
    {
        public ServiceException():base() { }

        public ServiceException(String msg) : base(msg) { }

        public ServiceException(String msg, Exception ex) : base(msg, ex) { }

    }
}
