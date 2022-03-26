using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Client
{
    public enum FTUserEvent
    {
        saveDRezervare,
    }     public class FTUserEventArgs : EventArgs
    {
        private readonly FTUserEvent userEvent;
        private readonly Object data;

        public FTUserEventArgs(FTUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public FTUserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
