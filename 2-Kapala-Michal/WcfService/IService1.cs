﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Security;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfService
{
    [ServiceContract(ProtectionLevel = ProtectionLevel.None)]
    public interface IService1
    {
        [OperationContract]
        double Add(double val1, double val2);
        [OperationContract]
        double Multiply(double val1, double val2);
        [OperationContract]
        double HMultiply(double val1, double val2);
    }
}
