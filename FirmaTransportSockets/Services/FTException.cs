using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class FTException : Exception
    {
        public FTException() : base() { }

        public FTException(String msg) : base(msg) { }

        public FTException(String msg, Exception ex) : base(msg, ex) { }

    }
}
