using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfService
{
    [ServiceContract]
    public interface IService1
    {
        [OperationContract]
        double Add(double val1, double val2);
        [OperationContract]
        double Multiply(double val1, double val2);
    }

}
